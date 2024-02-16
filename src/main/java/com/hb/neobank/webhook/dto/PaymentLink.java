package com.hb.neobank.webhook.dto;

import com.hb.neobank.razorpay.dto.RazorpayPaymentLinkResponseTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentLink {
    public RazorpayPaymentLinkResponseTO entity;
}
