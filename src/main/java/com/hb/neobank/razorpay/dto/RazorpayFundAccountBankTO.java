package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class RazorpayFundAccountBankTO {
    private String ifsc;
    private String bankName;
    private String name;
    private HashMap<String, String> notes;
    private String accountNumber;
}