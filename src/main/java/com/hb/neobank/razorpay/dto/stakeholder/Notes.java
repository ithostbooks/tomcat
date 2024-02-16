package com.hb.neobank.razorpay.dto.stakeholder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notes {
    @JsonProperty("random_key_by_partner")
    private String randomKey;

}
