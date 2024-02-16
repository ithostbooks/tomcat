package com.hb.neobank.razorpay.dto.stakeholder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Phone {

    @JsonProperty("primary")
    private  Long primary;
    @JsonProperty("secondary")
    private  Long secondary;
}
