package com.hb.neobank.icici.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "com.hostbook.icici")
public class IciciConfig {

	private String accountOpeningUser;

	private String accountOpeningPassCode;
	
	private String accountOpeningRegistrationAPI;
	
	private String accountOpeningMasterAPI;

	private String apiKey;

	private String proxyIP;

	private String hostIP;

	private String AGGRNAME;

	private String AGGRID;
	
	private String baseUrlAPI;

	private String registrationAPI;

	private String registrationStatusAPI;

	private String deRegistrationAPI;

	private String accountBalanceAPI;

	private String accountStatementAPI;

	private String txnOtpGenerationAPI;

	private String txnPaymentAPI;

	private String txnStatusAPI;
}
