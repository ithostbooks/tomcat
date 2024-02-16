package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequirementsTO {

    private  String field_reference;
    private  String status;
    private  String resolution_url;
    private  String reason_code;
    private  String description;

}
