package com.hb.neobank.accouting.service;

import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.dto.RazorpayReqResTO;

import java.util.HashMap;

public interface HbAccountingApiService {
    boolean statusChangeCallBack(HashMap<String, String> razorpayWebhookData, String uuid);

    boolean paymentStatusChangeCallBack(HashMap<String, String> razorpayWebhookData, String uuid);
}