package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RazorpayError {
    String code;
    String description;
    String field;
    String source;
    String step;
    String reason;
    Object metadata;
}



