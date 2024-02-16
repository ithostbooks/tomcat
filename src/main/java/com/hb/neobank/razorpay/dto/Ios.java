package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Ios {

    @JsonProperty("url")
    private String  url;
    @JsonProperty("name")
    private String  name;
}
