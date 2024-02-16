package com.hb.neobank.razorpay.service;

import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.dto.RazorpayReqResTO;
import com.hb.neobank.razorpay.dto.RazorpayTxnReqTO;
import com.hb.neobank.webhook.dto.RazorpayWebhookData;
import org.json.JSONObject;

public interface RazorpayPayoutService {

    ResponseDTO processPayment(RazorpayReqResTO razorpayPayout, String keyId, String keySecret) throws RazorpayApiException;

    ResponseDTO checkPayoutStatus(String payoutId, String keyId, String keySecret) throws RazorpayApiException;

    void webhookUpdate(RazorpayWebhookData data);

    ResponseDTO fetchTransactions(String accountNumber, String keyId, String keySecret, RazorpayTxnReqTO razorpayTxnReqTO) throws RazorpayApiException;
}
