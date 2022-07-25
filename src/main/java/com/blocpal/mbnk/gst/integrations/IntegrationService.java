package com.blocpal.mbnk.gst.integrations;

import com.blocpal.mbnk.gst.adapters.db.dao.TransactionDAO;
import com.blocpal.mbnk.gst.adapters.db.model.IntegrationCustInfo;
import com.blocpal.mbnk.gst.adapters.response.VerificationResponse;
import com.blocpal.mbnk.gst.adapters.wallets.WalletService;
import com.blocpal.mbnk.gst.adapters.wallets.model.UserInfo;
import com.blocpal.mbnk.gst.adapters.wallets.request.InitiateTxnRequest;
import com.blocpal.mbnk.gst.common.TransactionStatusCodes;
import com.blocpal.mbnk.gst.dao.models.GSTData;
import com.blocpal.mbnk.gst.dao.models.TransactionLog;
import com.blocpal.mbnk.gst.model.Transaction;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Singleton
public class IntegrationService {

    @Inject
    private WalletService walletService;

    @Inject
    private TransactionDAO transactionDAO;

    public void updateTxnErrorOnIntegrations(String txnId, String errorCode, String errorMessage){
        log.info(String.format("updateTxnInfoOnIntegrations with txnId: %s, errorCode: %s, errorMessage: %s",
                txnId,
                errorCode,
                errorMessage
        ));
        Map<String,Object> errorMap = new HashMap<>();
        errorMap.put("code", errorCode);
        errorMap.put("message", errorMessage);
        transactionDAO.updateTransaction(txnId, "F", errorMap);
        log.info("updateTxnInfoOnIntegrations success ");
    }
    public GSTData initiateTxnOnIntegrations(
            Transaction transaction,
            String serviceProviderId,
            String userId,
            String walletId
    ){
        log.info("initiateTxnOnIntegrations");

        log.info(String.format("Getting userInfo from wallets for userId: %s", userId));
        UserInfo userInfo = walletService.getUserInfo(userId);
        log.info(String.format("Received walletInfo: %s", userInfo.toString()));

        IntegrationCustInfo custInfo = new IntegrationCustInfo();
        custInfo.setGstNo(transaction.getGstin());
        custInfo.setCpin(transaction.getCpin());
        custInfo.setApiName(transaction.getApiName());

        GSTData data=new GSTData(transaction);
        log.info("Creating txn in integrations");
        String txnId = transactionDAO.createTradeTransaction(
                data,
                userInfo,
                serviceProviderId,
                custInfo,
                "P",
                walletId
        );
        data.setTxnId(txnId);
        return data;

    }

    public void updateTxnInfoInIntegrations(String txnId, VerificationResponse response, Map<String, Object> txnData){
        log.info(String.format("updateTxnInfoInIntegrations txnId: %s tradeStatus: %s txnData: %s",
                txnId,
                response.toString(),
                txnData.toString())
        );
        String statusStr = null;
        if(response.getData().getStatusDesc().equals(TransactionStatusCodes.SUCCESS)) {
            statusStr = TransactionStatusCodes.SUCCESS;
        }
        else if(response.getData().getStatusDesc().equals(TransactionStatusCodes.FAILURE)) {
            statusStr = TransactionStatusCodes.FAILURE;
        }
        else {
            statusStr = TransactionStatusCodes.PENDING;
        }
        transactionDAO.updateTransaction(txnId, statusStr, txnData);
        log.info("updateTxnInfoOnIntegrations complete ");
    }

    public void updateStatusInTxnLog(String txnId, String status) {
        transactionDAO.updateStatusInTxnLog(txnId,status);
    }

    public List<TransactionLog> getPendingTransactions() {
        return transactionDAO.getPendingTransactions();
    }

    public TransactionLog getTransaction(String txnId) {
        return transactionDAO.getTransaction(txnId);
    }
}
