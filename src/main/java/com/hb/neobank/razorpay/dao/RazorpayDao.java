package com.hb.neobank.razorpay.dao;

import com.hb.neobank.razorpay.model.RazorpayAccountBO;
import com.hb.neobank.razorpay.model.RazorpayApiCallHistoryBO;
import com.hb.neobank.razorpay.model.RazorpayContactBO;
import com.hb.neobank.razorpay.model.RazorpayPayoutBO;

public interface RazorpayDao {

    RazorpayApiCallHistoryBO addApiCallHistory(RazorpayApiCallHistoryBO historyData);

    RazorpayContactBO addRazorpayContact(RazorpayContactBO razorpayContact);

    RazorpayAccountBO addRazorpayAccount(RazorpayAccountBO razorpayAccount);

    RazorpayPayoutBO addRazorpayPayout(RazorpayPayoutBO razorpayPayout);

    RazorpayPayoutBO updateRazorpayPayout(RazorpayPayoutBO razorpayPayout);

    RazorpayPayoutBO fetchRazorpayPayoutById(String id);

    boolean isContactFound(String id);

    boolean isAccountFound(String id);
}
