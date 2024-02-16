package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RazorpayTxnTO {
    private String id;
    private String entity;
    private String accountNumber;
    private Integer amount;
    private Double amountRup;
    private String currency;
    private Integer credit;
    private Double creditRup;
    private Integer debit;
    private Double debitRup;
    private Integer balance;
    private Double balanceRup;
    private Integer createdAt;
    private RazorpayTxnSourceTO source;
}