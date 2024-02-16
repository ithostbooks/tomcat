package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RazorpayPayoutAccountTO {
    private String id;
    private String entity;
    private String contactId;
    private RazorpayPayoutContactTO contact;
    private String accountType;
    private RazorpayFundAccountBankTO bankAccount;
    private String batchId;
    private Boolean active;
    private Integer createdAt;
}

@Getter
@Setter
class FundAccountVpa {
    private String username;
    private String handle;
    private String address;
}

@Getter
@Setter
class FundAccountCard {
    private String name;
    private String last4;
    private String network;
    private String type;
    private String issuer;
}

