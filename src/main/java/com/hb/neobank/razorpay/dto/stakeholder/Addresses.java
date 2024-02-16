package com.hb.neobank.razorpay.dto.stakeholder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Addresses {
    @JsonProperty("residential")
    private Residential residential ;
}
