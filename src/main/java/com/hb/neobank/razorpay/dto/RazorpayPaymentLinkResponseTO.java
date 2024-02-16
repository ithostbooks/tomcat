package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RazorpayPaymentLinkResponseTO {
    private Double amountPaid;
    private Integer createdAt;
    private Integer cancelledAt;
    private Integer expiredAt;
    private String id;
    private Object reminders;
    private String shortUrl;
    private String status;
    private Integer updatedAt;
    private String userId;
    private RazorpayError error;
    private List<RazorpayPaymentLinksTO> paymentLinks;
    private Integer responseStatus;
    private Object payments;
    private String orderId;

    // Request
    private Double amount;
    private String currency;
    private Boolean acceptPartial;
    private Long firstMinPartialAmount;
    private Integer expireBy;
    private String referenceId;
    private String description;
    private Object customer;
    private Object notify;
    private Boolean reminderEnable;
    private Object notes;
    private String callbackUrl;
    private String callbackMethod;

}
