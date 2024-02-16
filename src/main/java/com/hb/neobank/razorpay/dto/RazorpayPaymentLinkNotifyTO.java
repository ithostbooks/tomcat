package com.hb.neobank.razorpay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RazorpayPaymentLinkNotifyTO {
    private Boolean sms;
    private Boolean email;
}
