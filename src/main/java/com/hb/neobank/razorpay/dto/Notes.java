package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Notes {
    @JsonProperty("internal_ref_id")
    private String internalRefId;
}
