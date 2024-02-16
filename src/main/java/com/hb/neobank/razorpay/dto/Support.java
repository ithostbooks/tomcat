package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Support {
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private Long phone;
    @JsonProperty("policy_url")
    private String policyUrl;
}
