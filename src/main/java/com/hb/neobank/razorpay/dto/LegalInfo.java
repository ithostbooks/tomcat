package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  LegalInfo {
    @JsonProperty("gst")
    private String gst;
    @JsonProperty("pan")
    private String pan;
    @JsonProperty("cin")
    private String cin;
}
