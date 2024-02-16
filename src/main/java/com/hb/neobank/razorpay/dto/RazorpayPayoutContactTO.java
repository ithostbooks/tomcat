package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;


@Setter
@Getter
public class RazorpayPayoutContactTO {
    private String id;
    private String entity;
    private String name;
    private String contact;
    private String email;
    private String type;
    private String referenceId;
    private String batchId;
    private Boolean active;
    private HashMap<String, String> notes;
    private Integer createdAt;
}