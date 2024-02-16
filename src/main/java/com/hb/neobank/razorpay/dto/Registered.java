package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hb.neobank.common.AuditTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Registered {

    @JsonProperty("street1")
    private String street1;
    @JsonProperty("street2")
    private String street2;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("postal_code")
    private Integer postal_code;
    @JsonProperty("country")
    private String country;
}
