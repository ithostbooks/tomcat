package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hb.neobank.common.AuditTO;
import com.hb.neobank.razorpay.dto.stakeholder.Residential;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    @JsonProperty("operation")
    private Operation operation ;
    @JsonProperty("registered")
    private Registered registered;

}