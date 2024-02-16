package com.hb.neobank.razorpay.dto.ProductConfig;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class SettlementsTO {
    private String account_number;
    private String ifsc_code;
    private String beneficiary_name;
}
