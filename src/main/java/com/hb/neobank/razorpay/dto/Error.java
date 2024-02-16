package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
    public  String code;
    public  String description;
    public  String source;
    public  String step;
    public  String reason;
    public  String field;
}
