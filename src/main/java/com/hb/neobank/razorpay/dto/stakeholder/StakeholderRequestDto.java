package com.hb.neobank.razorpay.dto.stakeholder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hb.neobank.razorpay.dto.Address;
import com.hb.neobank.razorpay.model.StakeholderRelationshipBO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class StakeholderRequestDto {
    @JsonProperty("email")
    private String Email;
    @JsonProperty("name")
    private String name;
    @JsonProperty("percentage_ownership")
    private BigDecimal percentage_ownership;

//    @JsonProperty("relationship")
//    private List<Relationship> relationship;
//
//    @JsonProperty("phone")
//    private  Phone phone;

    @JsonProperty("addresses")
    private Addresses addresses;

    @JsonProperty("kyc")
    private  Kyc kyc;

//    @JsonProperty("notes")
//    private Notes notes;


}
