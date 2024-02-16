package com.hb.neobank.webhook.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Payload {
    private Payout payout;
    private Payment payment;
    private PaymentLink payment_link;
    private Object transaction;
}
