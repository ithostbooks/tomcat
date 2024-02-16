package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Setter
@Getter
public class RazorpayReqResTO {
    private String accountNumber;
    private String id;
    private String entity;
    private String fundAccountId;
    private RazorpayPayoutAccountTO fundAccount;
    private Double fundAmount;
    private Integer amount;
    private String currency;
    private HashMap<String, String> notes;
    private Integer fees;
    private Integer tax;
    private String status;
    private String purpose;
    private String utr;
    private String mode;
    private String referenceId;
    private String narration;
    private String merchantId;
    private Boolean queueIfLowBalance;
    private RazorpayError error;
    private String uuid;
    private Integer count;
    private List<RazorpayTxnTO> items;
}