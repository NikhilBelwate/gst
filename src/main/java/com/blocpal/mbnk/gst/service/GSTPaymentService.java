package com.blocpal.mbnk.gst.service;

import com.blocpal.common.response.ServiceResult;
import com.blocpal.common.response.ServiceStatus;
import com.blocpal.common.utility.IdGenUtility;
import com.blocpal.common.wallet.request.RechargeInfo;
import com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintEndpoints;
import com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintService;
import com.blocpal.mbnk.gst.adapters.request.InquiryRequest;
import com.blocpal.mbnk.gst.adapters.request.ProcessRequest;
import com.blocpal.mbnk.gst.adapters.request.VerificationRequest;
import com.blocpal.mbnk.gst.adapters.response.DownloadResponse;
import com.blocpal.mbnk.gst.adapters.response.InquiryResponse;
import com.blocpal.mbnk.gst.adapters.response.ProcessResponse;
import com.blocpal.mbnk.gst.adapters.response.VerificationResponse;
import com.blocpal.mbnk.gst.adapters.wallets.WalletService;
import com.blocpal.mbnk.gst.adapters.wallets.model.TxnInfo;
import com.blocpal.mbnk.gst.adapters.wallets.request.InitiateTxnRequest;
import com.blocpal.mbnk.gst.adapters.wallets.request.UpdateTxnRequest;
import com.blocpal.mbnk.gst.dao.models.*;
import com.blocpal.mbnk.gst.dao.service.ServiceProviderDataDaoService;
import com.blocpal.mbnk.gst.dao.service.TransactionLogsDaoService;
import com.blocpal.mbnk.gst.exception.GstException;
import com.blocpal.mbnk.gst.integrations.IntegrationService;
import com.blocpal.mbnk.gst.response.GstInquiryResponse;
import com.google.cloud.Timestamp;
import io.micronaut.context.annotation.Bean;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintEndpoints.SubType;
import static com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintEndpoints.Type;


@Bean
@Slf4j
public class GSTPaymentService {
    @Inject
    private PaysprintService paysprintService;
    @Inject
    private GSTdbService gstDBService;
    @Inject
    private WalletService walletService;
    @Inject
    private ServiceProviderDataDaoService serviceProviderDataDaoService;

    @Inject
    private TransactionLogsDaoService txnLogService;

    @Inject
    private IntegrationService integrationService;

    private ServiceStatus serviceStatus;

    public GstInquiryResponse getInquiryResponse(InquiryRequest request, String retailerId, Boolean isNew) throws GstException {
        InquiryResponse response = paysprintService.getGstInquiryResponse(request, isNew);
        if (response != null) {
            if (response.getStatus())
                if (response.getResponse_code() == 0) {
                    ServiceProviderWalletsData walletData = serviceProviderDataDaoService.getServiceProvider(PaysprintEndpoints.SERVICE_NAME);

                    GSTData gstData = integrationService.initiateTxnOnIntegrations(response.getData(), walletData.getWalletId()
                            , retailerId, request.getRetailerWalletId());
                    return GstInquiryResponse.builder().amount(gstData.getAmount()).apiName(gstData.getApiName())
                            .gibTxnId(gstData.getGibTxnId()).txnId(gstData.getTxnId()).gstin(gstData.getGstin())
                            .cpin(gstData.getCpin()).transactionDetails(gstData.getTransactionDetails())
                            .isNew(isNew).build();
                }
        }
        throw new GstException(response.getResponse_code(), response.getMessage(), false);
    }

    public ProcessResponse getProcessResponse(ProcessRequest request, String retailerId) throws GstException {
        //1. get retailer wallet details
        ServiceProviderWalletsData walletData = serviceProviderDataDaoService.getServiceProvider(PaysprintEndpoints.SERVICE_NAME);
        //2. Create wallet object
        Map<String, String> wallets = new HashMap<>();
        wallets.put("retailer", retailerId);
        wallets.put("provider", walletData.getWalletId());
        //3. Get transaction ID from request
        String txnId = request.getTxnId();
        //4. initiate WalletTxn and get its result for further use
        TransactionLog txnLog = integrationService.getTransaction(txnId);
        InitiateTxnRequest initiateTxnRequest = InitiateTxnRequest.builder().clientTxnId(request.getGibTxnId())
                .txnAmt(txnLog.getA()).txnId(txnId).type(Type).subType(SubType).wallets(wallets).cur("INR")
                .txnInfo(TxnInfo.builder().cpin(request.getCpin()).retailerId(retailerId).build()).build();
        ServiceResult<Object> result = walletService.initiateWalletTxn(initiateTxnRequest);
        if (result.getStatusCode() != 0) {
            log.info("wallet txn not initiated");
            if (result.getMessage() != null)
                throw new GstException(3001, result.getMessage());
            else
                throw new GstException(3002, "Wallet related issue");
        } else {
            log.info("wallet txn initiated successfully");


            //6. Call Paysprint API for challan Payment
            ProcessResponse response = paysprintService.getGstProcessResponse(request);
            //7. set SP
            txnLog.setSp(ServiceProviderData.builder().gibTxnId(request.getGibTxnId()).blr(BillerData.builder().oId(request.getGibTxnId()).build()).id(walletData.getSpsId()).rm(response.getMessage()).tId(txnId).build());

            if (response != null) {
                if (!response.getStatus()) {
                    txnLog.setS(PaysprintEndpoints.walletConstant.FAILURE);
                    try {
                        updateRejectWallet(txnId, request, response);
                    } catch (Exception e) {
                        throw new GstException(500, "Failed to update transaction in wallet", false);
                    }
                } else if (response.getResponse_code() == 1) {
                    try {
                        updateConfirmedWallet(txnId, request, response);
                        txnLog.setS(PaysprintEndpoints.walletConstant.SUCCESS);
                    } catch (Exception e) {
                        txnLog.setS(PaysprintEndpoints.walletConstant.PENDING);
                        throw new GstException(501, "Failed to update transaction in wallet", false);
                    }
                } else {
                    txnLog.setS(PaysprintEndpoints.walletConstant.PENDING);
                }

                txnLogService.create(txnLog, "txns");
                return response;
                }else{
                throw new GstException(response.getResponse_code(), response.getMessage(), false);
                }
            }
    }

    public VerificationResponse doVerification(String referenceNumber) throws GstException {
        VerificationRequest request = new VerificationRequest();
        request.setReferenceNumber(referenceNumber);
        VerificationResponse response = paysprintService.getGstVerificationResponse(request);
        if (response != null) {
            return response;
        } else {
            throw new GstException(response.getResponse_code(), response.getMessage(), false);
        }
    }

    public DownloadResponse download(String referenceNumber) throws GstException {
        DownloadResponse response = paysprintService.getGstDownloadResponse(referenceNumber);
        if (response != null) {
            return response;
        } else {
            throw new GstException(response.getResponse_code(), response.getMessage(), false);
        }
    }

    public int doStatusUpdate() {
        try {
            List<TransactionLog> pendingTxnList = gstDBService.getPendingTransactions();
            AtomicInteger count = new AtomicInteger();
            pendingTxnList.forEach(transaction -> {
                try {
                    if (updatePendingTxnStatus(transaction))
                        count.getAndIncrement();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return count.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Boolean updatePendingTxnStatus(TransactionLog transactionLog) throws Exception {
        //This function return true if transaction is either FAILED or SUCCESS else return false
        VerificationRequest request = new VerificationRequest();
        request.setReferenceNumber(transactionLog.getSp().getGibTxnId());
        VerificationResponse response = paysprintService.getGstVerificationResponse(request);
        if (response != null) {
            if (PaysprintEndpoints.SUCCESS.equals(response.getData().getTransactionStatus())) {
                //Success
                updateConfirmedWallet(transactionLog, response);
                return true;
            } else if (PaysprintEndpoints.FAILED.equals(response.getData().getTransactionStatus())) {
                //Failed
                updateRejectWallet(transactionLog, response);
                return true;
            } else {
                //Pending
                return false;
            }
        } else {
            return false;
        }
    }

    private void updateConfirmedWallet(String txnId, ProcessRequest request, ProcessResponse response) throws Exception {
        Map<String, String> extIds = new HashMap<>();
        extIds.put("tId", txnId);
        extIds.put("GibTxnId", response.getData().getGibTxnId());
        extIds.put("cpin", request.getCpin());
        walletService.updateWalletTxn(UpdateTxnRequest.builder().txnId(txnId).status("CONFIRMED").extIds(extIds).statusMessage(response.getMessage()).build());
        gstDBService.updateStatusInTxnLog(txnId, PaysprintEndpoints.SUCCESS);
    }

    private void updateConfirmedWallet(TransactionLog transaction, VerificationResponse response) throws Exception {
        Map<String, String> extIds = new HashMap<>();
        extIds.put("tId", transaction.getSp().gettId());
        extIds.put("cpin", transaction.getCus().getCpin());
        extIds.put("GibTxnId", response.getData().getGibTxnId());
        walletService.updateWalletTxn(UpdateTxnRequest.builder().txnId(transaction.getSp().gettId()).status("CONFIRMED").extIds(extIds).statusMessage(response.getMessage()).build());
        gstDBService.updateStatusInTxnLog(transaction.getSp().gettId(), PaysprintEndpoints.SUCCESS);
    }

    private void updateRejectWallet(String txnId, ProcessRequest request, ProcessResponse response) throws Exception {
        walletService.updateWalletTxn(UpdateTxnRequest.builder().txnId(txnId).status("REJECT").statusMessage(response.getMessage()).build());
        gstDBService.updateStatusInTxnLog(txnId, PaysprintEndpoints.FAILED);
    }

    private void updateRejectWallet(TransactionLog transaction, VerificationResponse response) throws Exception {
        walletService.updateWalletTxn(UpdateTxnRequest.builder().txnId(transaction.getSp().gettId()).status("REJECT").statusMessage(response.getMessage()).build());
        gstDBService.updateStatusInTxnLog(transaction.getSp().gettId(), PaysprintEndpoints.FAILED);
    }

    public String getPDFStream(String referenceNumber) {
        return "JVBERi0xLjQKJeLjz9MKMSAwIG9iago8PC9UeXBlL1hPYmplY3QvQ29sb3JTcGFjZVsvSW5kZXhl\\nZC9EZXZpY2VSR0IgMjU1KPBpXCnD1OPw9PhNf6qIqcYgX5XS3";
    }

}
