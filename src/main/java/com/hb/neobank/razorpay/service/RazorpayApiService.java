package com.hb.neobank.razorpay.service;

import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.dto.RazorpayPaymentLinkReqTO;
import com.hb.neobank.razorpay.dto.RazorpayPaymentLinkResponseTO;
import com.hb.neobank.razorpay.dto.RazorpayReqResTO;

public interface RazorpayApiService {
    RazorpayReqResTO processPayment(RazorpayReqResTO razorpayPayout, String keyId, String keySecret) throws RazorpayApiException;

    RazorpayReqResTO checkPayoutStatus(String payoutId, String keyId, String keySecret) throws RazorpayApiException;

    RazorpayReqResTO fetchTransactions(String accountNumber, String keyId, String keySecret, Long fromDate, Long toDate, Integer count, Integer skip) throws RazorpayApiException;

    RazorpayPaymentLinkResponseTO createPaymentLink(RazorpayPaymentLinkReqTO razorpayPaymentLinkReqTO, String subMerchantAccId) throws RazorpayApiException;

    RazorpayPaymentLinkResponseTO cancelPaymentLink(String id, String subMerchantAccId);

    RazorpayPaymentLinkResponseTO fetchPaymentLink(String id, String subMerchantAccId);
}