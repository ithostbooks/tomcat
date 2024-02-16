package com.hb.neobank.razorpay.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountDto {
    private String email;
    private Long phone;
    private String legal_business_name;
    private String business_type;
    public boolean isDefaultFlag;
    private String customer_facing_business_name;
    private String contact_name;
    private Profile profile;
    @JsonProperty("legal_info")
    private LegalInfo legal_info;
    @JsonProperty("brand")
    private Brand brand;
    @JsonProperty("notes")
    private Notes notes;
    @JsonProperty("contact_info")
    private ContactInfo contact_info;
    @JsonProperty("apps")
    private Apps apps;
}






