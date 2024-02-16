package com.hb.neobank.razorpay.service;

import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.dto.RazorpayPaymentLinkReqTO;
import com.hb.neobank.webhook.dto.RazorpayWebhookData;

import java.text.ParseException;
import java.util.HashMap;

public interface RazorpayPaymentLinkService {

    ResponseDTO createPaymentLink(RazorpayPaymentLinkReqTO paymentLinkReq, String subMerchantAccId) throws RazorpayApiException, ParseException;


    ResponseDTO cancelPaymentLink(String id, String subMerchantAccId);


    ResponseDTO fetchPaymentLink(String id, String subMerchantAccId);

    void paymentWebhook(RazorpayWebhookData data);

    void accountWebhook(RazorpayWebhookData data);

}
