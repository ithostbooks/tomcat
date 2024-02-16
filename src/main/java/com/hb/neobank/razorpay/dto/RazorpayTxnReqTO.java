package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class RazorpayTxnReqTO {
    private Long fromDate;
    private Long toDate;
    private Integer limit;
    private Integer page;
}