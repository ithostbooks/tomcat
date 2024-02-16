package com.hb.neobank.accouting.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "com.hostbook.accounting")
public class HbAccountingConfig {
    private String baseUrlAPI;
    private String razorpayWebhookAPI;
    private String razorpayWebhookPaymentAPI;
}
