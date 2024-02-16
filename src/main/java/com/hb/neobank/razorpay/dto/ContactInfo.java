package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
class ContactInfo {

    @JsonProperty("chargeback")
    public Chargeback chargeBack;
    @JsonProperty("refund")
    public Refund refund;
    @JsonProperty("support")
    public Support support;
}
