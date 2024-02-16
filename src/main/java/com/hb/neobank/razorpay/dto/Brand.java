package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Brand {

    @JsonProperty("color")
    private String color;
}
