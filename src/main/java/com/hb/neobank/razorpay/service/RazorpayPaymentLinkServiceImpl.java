package com.hb.neobank.razorpay.service;

import com.hb.neobank.accouting.service.HbAccountingApiService;
import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.common.RazorpayConfig;
import com.hb.neobank.razorpay.dao.AccountDao;
import com.hb.neobank.razorpay.dao.RazorpayPaymentLinkDao;
import com.hb.neobank.razorpay.dto.*;
import com.hb.neobank.razorpay.model.RazorpayPaymentLinksBO;
import com.hb.neobank.razorpay.model.RazorpayPayoutBO;
import com.hb.neobank.razorpay.model.SubMerchantBO;
import com.hb.neobank.util.CommonUtil;
import com.hb.neobank.webhook.dto.Entity;
import com.hb.neobank.webhook.dto.PaymentEntity;
import com.hb.neobank.webhook.dto.RazorpayWebhookData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RazorpayPaymentLinkServiceImpl implements RazorpayPaymentLinkService {
    @Autowired
    RazorpayApiService razorpayApiService;

    @Autowired
    RazorpayConfig razorpayConfig;

    @Autowired
    HbAccountingApiService hbAccountingApiService;

    @Autowired
    RazorpayPaymentLinkDao razorpayPaymentLinkDao;

    ModelMapper mapper = new ModelMapper();

    @Autowired
    AccountDao accountDao;

    @Override
    public ResponseDTO createPaymentLink(RazorpayPaymentLinkReqTO paymentLinkReq, String subMerchantAccId) throws RazorpayApiException {
        ResponseDTO response = new ResponseDTO();
        response.setPath("/razorpay-payment-link");
        String uuid = paymentLinkReq.getUuid();
        paymentLinkReq.setUuid(null);
        this.generateRequest(paymentLinkReq);
        RazorpayPaymentLinkResponseTO razorpayRes = razorpayApiService.createPaymentLink(paymentLinkReq, subMerchantAccId);
        if (razorpayRes != null) {
            if (razorpayRes.getError() != null
                    && razorpayRes.getError().getCode() != null
                    && !razorpayRes.getError().getCode().equals("NA")
                    && razorpayRes.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(razorpayRes.getError().getDescription());
            } else {
                response.setStatus(200);
                if (razorpayRes.getError() == null) this.convertResponse(razorpayRes);
                RazorpayPaymentLinksBO razorpayPaymentLink = mapper.map(razorpayRes, RazorpayPaymentLinksBO.class);
                Map<String, Object> contactDetails = (Map) razorpayRes.getCustomer();
                if (contactDetails != null && !contactDetails.isEmpty()) {
                    if (contactDetails.get("name") != null)
                        razorpayPaymentLink.setName(contactDetails.get("name").toString());
                    if (contactDetails.get("email") != null)
                        razorpayPaymentLink.setEmail(contactDetails.get("email").toString());
                    if (contactDetails.get("contact") != null)
                        razorpayPaymentLink.setContact(contactDetails.get("contact").toString());
                }
                razorpayPaymentLink.setPaymentId(null);
                razorpayPaymentLink.setSecretId(null);
                razorpayPaymentLink.setUuid(uuid);
                razorpayPaymentLinkDao.addRazorpayPaymentLink(razorpayPaymentLink);
                response.putData("response", razorpayRes);
                response.setMessage("Payment Link Created Successfully");
            }
        }
        return response;
    }

    @Override
    public ResponseDTO cancelPaymentLink(String id, String subMerchantAccId) {
        ResponseDTO response = new ResponseDTO();
        response.setPath("/razorpay-payment-link");
        RazorpayPaymentLinksBO razorpayPaymentLinksBO = razorpayPaymentLinkDao.getRazorpayPaymentLinksByPlinkId(id);
        RazorpayPaymentLinkResponseTO razorpayRes = razorpayApiService.cancelPaymentLink(id, subMerchantAccId);
        if (razorpayRes != null) {
            if (razorpayRes.getError() != null
                    && razorpayRes.getError().getCode() != null
                    && !razorpayRes.getError().getCode().equals("NA")
                    && razorpayRes.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(razorpayRes.getError().getDescription());
            } else {
                response.setStatus(200);
                if (razorpayRes.getError() == null) this.convertResponse(razorpayRes);
                mapper.map(razorpayRes, razorpayPaymentLinksBO);
                razorpayPaymentLinksBO.setSecretId(null);
                razorpayPaymentLinksBO.setPaymentId(null);
                razorpayPaymentLinkDao.updateRazorpayPaymentLink(razorpayPaymentLinksBO);
                response.putData("response", razorpayRes);
                response.setMessage("Payment Link Cancelled Successfully");
            }
        }

        return response;
    }

    @Override
    public ResponseDTO fetchPaymentLink(String id, String subMerchantAccId) {
        ResponseDTO response = new ResponseDTO();
        response.setPath("/razorpay-payment-link");
        RazorpayPaymentLinkResponseTO razorpayRes = razorpayApiService.fetchPaymentLink(id, subMerchantAccId);
        if (razorpayRes != null) {
            if (razorpayRes.getError() != null
                    && razorpayRes.getError().getCode() != null
                    && !razorpayRes.getError().getCode().equals("NA")
                    && razorpayRes.getError().getDescription() != null) {
                response.setStatus(400);
                response.setMessage(razorpayRes.getError().getDescription());
            } else {
                response.setStatus(200);
                if (razorpayRes.getError() == null) this.convertResponse(razorpayRes);
                if (id != null) {
                    RazorpayPaymentLinksBO razorpayPaymentLinksBO = razorpayPaymentLinkDao.getRazorpayPaymentLinksByPlinkId(id);
                    if (razorpayPaymentLinksBO != null && (razorpayRes.getStatus().equals("paid") || razorpayRes.getAmountPaid() != razorpayPaymentLinksBO.getAmountPaid())) {
                        mapper.map(razorpayRes, razorpayPaymentLinksBO);
                        List payments = (List) razorpayRes.getPayments();
                        if (payments != null && !payments.isEmpty()) {
                            Map<String, Object> recentPayment = (Map) payments.get(payments.size() - 1);
                            if (recentPayment.get("payment_id") != null)
                                razorpayPaymentLinksBO.setPaymentId(recentPayment.get("payment_id").toString());
                        }
                        razorpayPaymentLinkDao.updateRazorpayPaymentLink(razorpayPaymentLinksBO);
                    }
                }
                response.putData("response", razorpayRes);
                response.setMessage("Payment Links fetched Successfully");
            }
        }

        return response;
    }

    public void paymentWebhook(RazorpayWebhookData data) {
        if (data != null && data.getEvent().equalsIgnoreCase("payment_link.paid")) {
            if (data.getPayload() != null && data.getPayload().getPayment() != null && data.getPayload().getPayment_link() != null
                    && data.getPayload().getPayment().getEntity() != null && data.getPayload().getPayment_link().getEntity() != null) {
                PaymentEntity paymentEntity = data.getPayload().getPayment().getEntity();
                RazorpayPaymentLinkResponseTO paymentLinkEntity = data.getPayload().getPayment_link().getEntity();
                RazorpayPaymentLinksBO razorpayPaymentLinksBO = razorpayPaymentLinkDao.fetchRazorpayPaymentLinkById(paymentLinkEntity.getId());
                if (razorpayPaymentLinksBO != null && !razorpayPaymentLinksBO.getStatus().equalsIgnoreCase(paymentLinkEntity.getStatus())) {
                    HashMap<String, String> paymentWebhookData = new HashMap<>();
                    paymentWebhookData.put("paymentStatus", paymentLinkEntity.getStatus());
                    paymentWebhookData.put("paymentId", paymentLinkEntity.getId());
                    boolean isHbCallBack = this.hbAccountingApiService.paymentStatusChangeCallBack(paymentWebhookData, razorpayPaymentLinksBO.getUuid());
                    if (isHbCallBack) {
                        razorpayPaymentLinksBO.setStatus(paymentLinkEntity.getStatus());
                        razorpayPaymentLinkDao.updateRazorpayPaymentLink(razorpayPaymentLinksBO);
                    }
                }
            }
        }
    }

    public void accountWebhook(RazorpayWebhookData data) {
        if (CommonUtil.checkNullEmpty(data)) {
            SubMerchantBO subMerchant = this.accountDao.getSubMerchantById(data.getAccount_id());
            if(data.getEvent().contains("account")){
                setStatus(data.getEvent(),subMerchant);
            }
            this.accountDao.updateSubMerchant(subMerchant);
        }
    }

    public void convertResponse(Object paymentLinkRes) {
        if (paymentLinkRes instanceof RazorpayPaymentLinkResponseTO) {
            RazorpayPaymentLinkResponseTO responseTO = (RazorpayPaymentLinkResponseTO) paymentLinkRes;
            if (responseTO.getPaymentLinks() != null) {
                for (RazorpayPaymentLinksTO paymentLinksTO : responseTO.getPaymentLinks()) {
                    convertResponse(paymentLinksTO);
                }
            }
            if (responseTO.getAmount() != null)
                responseTO.setAmount(responseTO.getAmount() / 100.00);
            if (responseTO.getAmountPaid() != null)
                responseTO.setAmountPaid(responseTO.getAmountPaid() / 100.00);
            List payments = (List) responseTO.getPayments();
            if (payments != null && !payments.isEmpty()) {
                for (Object payment : payments) {
                    Map<String, Object> paymentMap = (Map) payment;
                    paymentMap.put("amount", ((Integer) paymentMap.get("amount")) / 100.00);
                }
            }

        }
        if (paymentLinkRes instanceof RazorpayPaymentLinksTO) {
            RazorpayPaymentLinksTO responseTO = (RazorpayPaymentLinksTO) paymentLinkRes;
            if (responseTO.getAmount() != null)
                responseTO.setAmount(responseTO.getAmount() / 100.00);
            if (responseTO.getAmountPaid() != null)
                responseTO.setAmountPaid(responseTO.getAmountPaid() / 100.00);
            if (responseTO.getPayments() != null) {
                List payments = (List) responseTO.getPayments();
            }
        }
    }

    private void generateRequest(RazorpayPaymentLinkReqTO paymentLinkReq) {
        paymentLinkReq.setCurrency("INR");
        if (paymentLinkReq.getReferenceId() == null) paymentLinkReq.setReferenceId(Double.toString(Math.random()));
//        if (paymentLinkReq.getCallbackUrl() == null) paymentLinkReq.setCallbackUrl(razorpayConfig.getCallbackUrl());
        RazorpayPaymentLinkNotifyTO notify = new RazorpayPaymentLinkNotifyTO(true, true);
        paymentLinkReq.setNotify(notify);
        paymentLinkReq.setReminderEnable(true);
        paymentLinkReq.setCallbackMethod("get");
        paymentLinkReq.setAcceptPartial(false);
    }

    public void setStatus(String status,SubMerchantBO subMerchant){
        switch (status){
            case "account.activated":{
                subMerchant.setStatus("Activated");
                break;
            }
            case "account.needs_clarification":{
                subMerchant.setStatus("Needs Clarification");
                break;
            }
            case "account.rejected":{
                subMerchant.setStatus("Rejected");
                break;
            }
            case"account.activated_kyc_pending":{
                subMerchant.setStatus("Activated Kyc Pending");
                break;
            }
        }
    }
}
