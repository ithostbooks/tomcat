package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class RazorpayTxnSourceTO {
    private String id;
    private String entity;
    private Integer amount;
    private Double amountRup;
    private HashMap<String, String> notes;
    private Integer fees;
    private Integer tax;
    private String status;
    private String utr;
    private String mode;
    private Integer createdAt;
    private String feeType;
}
