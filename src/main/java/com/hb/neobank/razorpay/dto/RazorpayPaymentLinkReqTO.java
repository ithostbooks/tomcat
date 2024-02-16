package com.hb.neobank.razorpay.dto;

import com.hb.neobank.razorpay.dto.RazorpayPaymentLinkCustomerTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RazorpayPaymentLinkReqTO {
    // Request
    private Double amount;
    private String currency;
    private Boolean acceptPartial;
    private Double firstMinPartialAmount;
    private Integer expireBy;
    private String referenceId;
    private String description;
    private RazorpayPaymentLinkCustomerTO customer;
    private RazorpayPaymentLinkNotifyTO notify;
    private Boolean reminderEnable;
    private Object notes;
    private String callbackUrl;
    private String callbackMethod;
    private String uuid;
}
