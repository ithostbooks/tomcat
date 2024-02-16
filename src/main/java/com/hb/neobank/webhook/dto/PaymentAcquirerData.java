package com.hb.neobank.webhook.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentAcquirerData {
    private String bank_transaction_id;
}
