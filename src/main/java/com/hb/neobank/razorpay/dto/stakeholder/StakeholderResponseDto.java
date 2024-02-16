package com.hb.neobank.razorpay.dto.stakeholder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hb.neobank.razorpay.dto.RazorpayError;
import com.hb.neobank.razorpay.model.StakeholderRelationshipBO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class StakeholderResponseDto {

    @JsonProperty("id")
    private String id;
    @JsonProperty("entity")
    private String Entity;
    @JsonProperty("email")
    private String Email;
    @JsonProperty("name")
    private String Name;
    @JsonProperty("percentage_ownership")
    private BigDecimal percentage_ownership;
    @JsonProperty("addresses")
    private Addresses addresses;
    @JsonProperty("kyc")
    private Kyc kyc;
    @JsonProperty("status")
    private String status;

//    List<Relationship> relationship;

    private RazorpayError error;
//
//    @JsonProperty("phone")
//    private Phone phone;
}
