package com.hb.neobank.razorpay.dao;

import com.hb.neobank.razorpay.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RazorpayPaymentLinkDao {

    RazorpayApiCallHistoryBO addApiCallHistory(RazorpayApiCallHistoryBO historyData);

    RazorpayPaymentLinksBO addRazorpayPaymentLink(RazorpayPaymentLinksBO razorpayPayLink);

    RazorpayPaymentLinksBO updateRazorpayPaymentLink(RazorpayPaymentLinksBO razorpayPayLink);

    List<RazorpayPaymentLinksBO> getAllRzpPaymentLinks();

    RazorpayPaymentLinksBO getRazorpayPaymentLinksByPlinkId(String pLinkId);

    RazorpayPaymentLinksBO fetchRazorpayPaymentLinkById(String id);

}
