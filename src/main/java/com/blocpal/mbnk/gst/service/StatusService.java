package com.blocpal.mbnk.gst.service;

import com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintEndpoints;
import com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintService;
import com.blocpal.mbnk.gst.adapters.request.VerificationRequest;
import com.blocpal.mbnk.gst.adapters.response.VerificationResponse;
import com.blocpal.mbnk.gst.adapters.wallets.WalletService;
import com.blocpal.mbnk.gst.adapters.wallets.request.UpdateTxnRequest;
import com.blocpal.mbnk.gst.dao.models.TransactionLog;
import com.blocpal.mbnk.gst.integrations.IntegrationService;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Bean
@Slf4j
public class StatusService {
    @Inject
    IntegrationService integrationService;

    @Inject
    PaysprintService paysprintService;
    @Inject
    WalletService walletService;
    public int doStatusUpdate() {
        try {
            List<TransactionLog> pendingTxnList = integrationService.getPendingTransactions();
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
    private void updateConfirmedWallet(TransactionLog transaction, VerificationResponse response) throws Exception {
        Map<String, String> extIds = new HashMap<>();
        extIds.put("tId", transaction.getSp().gettId());
        extIds.put("cpin", transaction.getCus().getCpin());
        extIds.put("GibTxnId", response.getData().getGibTxnId());
        walletService.updateWalletTxn(UpdateTxnRequest.builder().txnId(transaction.getSp().gettId()).status("CONFIRMED").extIds(extIds).statusMessage(response.getMessage()).build());
        integrationService.updateStatusInTxnLog(transaction.getSp().gettId(), PaysprintEndpoints.SUCCESS);
    }
    private void updateRejectWallet(TransactionLog transaction, VerificationResponse response) throws Exception {
        walletService.updateWalletTxn(UpdateTxnRequest.builder().txnId(transaction.getSp().gettId()).status("REJECT").statusMessage(response.getMessage()).build());
        integrationService.updateStatusInTxnLog(transaction.getSp().gettId(), PaysprintEndpoints.FAILED);
    }

}
