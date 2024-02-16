package com.hb.neobank.yesbank.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "com.hostbook.yesbank")
public class YesBankConfig {

    private String clientId;

    private String clientSecret;

    private String ft3UserName;

    private String ft3UserPassword;

    private String adhocUserName;

    private String adhocUserPassword;

    private String baseUrlAPI;

    private String paymentAPI;

    private String fundConfirmationAPI;

    private String paymentDetailAPI;

    private String accountStatementAPI;
}
