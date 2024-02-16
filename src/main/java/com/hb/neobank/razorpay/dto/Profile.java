package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hb.neobank.common.AuditTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public
class Profile {
    @JsonProperty("category")
    private String category;
    @JsonProperty("subcategory")
    private String subcategory;
    @JsonProperty("description")
    private String description;
    @JsonProperty("addresses")
    private  Address addresses;
//    @JsonProperty("business_model")
//    private String business_model;
}
