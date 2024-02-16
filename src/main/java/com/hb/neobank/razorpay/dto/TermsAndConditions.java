package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermsAndConditions {

    @JsonProperty("id")
    private String id;
    @JsonProperty("terms")
    private String terms;
    @JsonProperty("privacy")
    private Integer privacy;
    @JsonProperty("agreement")
    private Integer agreement;
}
