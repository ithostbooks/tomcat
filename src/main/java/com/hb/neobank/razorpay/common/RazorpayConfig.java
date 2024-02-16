package com.hb.neobank.razorpay.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "com.hostbook.razorpay")
public class RazorpayConfig {
    private String baseUrlAPI;
    private String payoutContactAPI;
    private String payoutFundAccountAPI;
    private String payoutAPI;
    private String transactionsAPI;
    private String paymentLinksAPI;
    private String cancelPaymentLinksAPI;
    private String keyId;
    private String keySecret;
}
