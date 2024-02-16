package com.hb.neobank.razorpay.dto.ProductConfig;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Requirements {
    private String field_reference;
    private String resolution_url;
    private String status;
    private String reason_code;

}
