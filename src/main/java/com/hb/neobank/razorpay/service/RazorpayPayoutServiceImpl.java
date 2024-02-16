package com.hb.neobank.razorpay.service;

import com.hb.neobank.accouting.service.HbAccountingApiService;
import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.dao.RazorpayDao;
import com.hb.neobank.razorpay.dto.RazorpayReqResTO;
import com.hb.neobank.razorpay.dto.RazorpayTxnReqTO;
import com.hb.neobank.razorpay.model.RazorpayAccountBO;
import com.hb.neobank.razorpay.model.RazorpayContactBO;
import com.hb.neobank.razorpay.model.RazorpayPayoutBO;
import com.hb.neobank.webhook.dto.Entity;
import com.hb.neobank.webhook.dto.RazorpayWebhookData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
@Transactional
public class RazorpayPayoutServiceImpl implements RazorpayPayoutService {

    @Autowired
    RazorpayApiService razorpayApiService;

    @Autowired
    HbAccountingApiService hbAccountingApiService;

    @Autowired
    private RazorpayDao razorpayDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseDTO processPayment(RazorpayReqResTO razorpayPayout, String keyId, String keySecret) throws RazorpayApiException {
        String uuid = razorpayPayout.getUuid();
        razorpayPayout.setUuid(null);
        razorpayPayout.setCurrency("INR");
        ResponseDTO response = new ResponseDTO();
        response.setPath("/razorpay-payout");
        RazorpayReqResTO razorpayRes = razorpayApiService.processPayment(razorpayPayout, keyId, keySecret);
        if (razorpayRes != null) {
            if (razorpayRes.getError() != null
                    && razorpayRes.getError().getCode() != null
                    && !razorpayRes.getError().getCode().equals("NA")
                    && razorpayRes.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(razorpayRes.getError().getDescription());
            } else {
                response.setStatus(200);
                if (razorpayRes.getFundAccount() != null
                        && razorpayRes.getFundAccount().getContact() != null) {
                    String contactId = razorpayRes.getFundAccount().getContact().getId();
                    String accountId = razorpayRes.getFundAccount().getId();
                    if (!razorpayDao.isContactFound(contactId)) {
                        RazorpayContactBO razorpayContact = modelMapper.map(razorpayRes.getFundAccount().getContact(), RazorpayContactBO.class);
                        razorpayContact.setRazorpayxAccount(razorpayPayout.getAccountNumber());
                        razorpayDao.addRazorpayContact(razorpayContact);
                    }
                    if (!razorpayDao.isAccountFound(accountId)) {
                        RazorpayAccountBO razorpayAccount = modelMapper.map(razorpayRes.getFundAccount(), RazorpayAccountBO.class);
                        razorpayAccount.setContactId(contactId);
                        razorpayAccount.setAccountType(razorpayRes.getFundAccount().getAccountType());
                        if (razorpayRes.getFundAccount().getBankAccount() != null) {
                            razorpayAccount.setAccountHolderName(razorpayRes.getFundAccount().getBankAccount().getName());
                            razorpayAccount.setBankAccountNumber(razorpayRes.getFundAccount().getBankAccount().getAccountNumber());
                            razorpayAccount.setBankAccountIfsc(razorpayRes.getFundAccount().getBankAccount().getIfsc());
                            razorpayAccount.setBankAccountName(razorpayRes.getFundAccount().getBankAccount().getBankName());
                        }
                        razorpayDao.addRazorpayAccount(razorpayAccount);
                    }
                    RazorpayPayoutBO razorpay = modelMapper.map(razorpayRes, RazorpayPayoutBO.class);
                    razorpay.setFundAccountId(razorpayRes.getFundAccountId());
                    razorpay.setReferenceId(razorpayRes.getReferenceId());
                    razorpay.setMerchantId(razorpayRes.getMerchantId());
                    razorpay.setRazorpayxAccount(razorpayPayout.getAccountNumber());
                    razorpay.setUuid(uuid);
                    razorpayDao.addRazorpayPayout(razorpay);
                }
                response.setMessage("Payment Process Successfully");
                response.putData("payoutStatus", razorpayRes.getStatus());
                response.putData("payoutId", razorpayRes.getId());
            }
        }
        return response;
    }

    @Override
    public ResponseDTO checkPayoutStatus(String payoutId, String keyId, String keySecret) throws RazorpayApiException {
        ResponseDTO response = new ResponseDTO();
        response.setPath("/razorpay-payout");
        RazorpayReqResTO razorpayRes = razorpayApiService.checkPayoutStatus(payoutId, keyId, keySecret);
        if (razorpayRes != null) {
            if (razorpayRes.getError() != null
                    && razorpayRes.getError().getCode() != null
                    && !razorpayRes.getError().getCode().equals("NA")
                    && razorpayRes.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(razorpayRes.getError().getDescription());
            } else {
                response.setStatus(200);
                if (razorpayRes.getStatus() != null) {
                    RazorpayPayoutBO razorpay = razorpayDao.fetchRazorpayPayoutById(payoutId);
                    razorpay.setStatus(razorpayRes.getStatus());
                    razorpay.setUtr(razorpayRes.getUtr());
                    razorpayDao.updateRazorpayPayout(razorpay);
                }
                response.setMessage("Payment Status Fetched Successfully");
                response.putData("payoutStatus", razorpayRes.getStatus());
                response.putData("payoutId", razorpayRes.getId());
                response.putData("utr", razorpayRes.getUtr());
            }
        }
        return response;
    }

    @Override
    public ResponseDTO fetchTransactions(String accountNumber, String keyId, String keySecret, RazorpayTxnReqTO razorpayTxnReqTO) throws RazorpayApiException {
        ResponseDTO response = new ResponseDTO();
        response.setPath("/razorpay-payout");
        Integer count = null;
        Integer skip = null;
        if (razorpayTxnReqTO != null) {
            if (razorpayTxnReqTO.getLimit() != null) {
                count = razorpayTxnReqTO.getLimit();
                if (razorpayTxnReqTO.getPage() != null) {
                    skip = (razorpayTxnReqTO.getPage() - 1) * razorpayTxnReqTO.getLimit();
                }
            }
        }
        RazorpayReqResTO razorpayRes = razorpayApiService.fetchTransactions(accountNumber, keyId, keySecret, razorpayTxnReqTO.getFromDate(), razorpayTxnReqTO.getToDate(), count, skip);
        if (razorpayRes != null) {
            if (razorpayRes.getError() != null
                    && razorpayRes.getError().getCode() != null
                    && !razorpayRes.getError().getCode().equals("NA")
                    && razorpayRes.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(razorpayRes.getError().getDescription());
            } else {
                response.setStatus(200);
                if (razorpayRes.getItems() != null && !razorpayRes.getItems().isEmpty()) {
                    razorpayRes.getItems().forEach(item -> {
                        if (item.getAmount() != null) {
                            item.setAmountRup((item.getAmount().intValue() / 100.00));
                        }
                        if (item.getDebit() != null) {
                            item.setDebitRup((item.getDebit().intValue() / 100.00));
                        }
                        if (item.getCredit() != null) {
                            item.setCreditRup((item.getCredit().intValue() / 100.00));
                        }
                        if (item.getBalance() != null) {
                            item.setBalanceRup((item.getBalance().intValue() / 100.00));
                        }
                        if (item.getSource() != null && item.getSource().getAmount() != null) {
                            item.getSource().setAmountRup((item.getSource().getAmount().intValue() / 100.00));
                        }
                    });
                }
                response.setMessage("Transactions Fetched Successfully");
                response.putData("transactionList", razorpayRes.getItems());
                response.putData("count", razorpayRes.getCount());
            }
        }
        return response;
    }

    @Override
    public void webhookUpdate(RazorpayWebhookData data) {
        if (data != null && data.getPayload() != null && data.getPayload().getPayout() != null
                && data.getPayload().getPayout().getEntity() != null && data.getPayload().getPayout().getEntity().getId() != null) {
            Entity entity = data.getPayload().getPayout().getEntity();
            RazorpayPayoutBO razorpay = razorpayDao.fetchRazorpayPayoutById(entity.getId());
            if (razorpay != null && !razorpay.getStatus().equals(entity.getStatus())) {
                HashMap<String, String> razorpayWebhookData = new HashMap<>();
                razorpayWebhookData.put("payoutStatus", entity.getStatus());
                razorpayWebhookData.put("payoutId", entity.getId());
                String statusDetails = null;
                if (entity.getStatus_details() != null) {
                    statusDetails = entity.getStatus_details().getDescription();
                    razorpayWebhookData.put("statusDetails", statusDetails);
                }
                boolean isHbCallBack = this.hbAccountingApiService.statusChangeCallBack(razorpayWebhookData, razorpay.getUuid());
                if (isHbCallBack) {
                    razorpay.setStatus(entity.getStatus());
                    razorpay.setUtr(entity.getUtr());
                    if (statusDetails != null) {
                        razorpay.setStatusDetails(statusDetails);
                    }
                    razorpayDao.updateRazorpayPayout(razorpay);
                }
            }
        }
    }
}
