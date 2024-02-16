package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hb.neobank.common.AuditTO;
import com.hb.neobank.razorpay.dto.stakeholder.StakeholderRequestDto;
import com.hb.neobank.razorpay.dto.stakeholder.StakeholderResponseDto;
import com.hb.neobank.razorpay.model.TermsAndConditions;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SubMerchantTO {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("status")
    private String status;
    @JsonProperty("email")
    private String email;
    @JsonProperty("defaultFlag")
    private boolean defaultFlag;
    @JsonProperty("phone")
    private Long phone;
    @JsonProperty("legal_business_name")
    private String legal_business_name;
    @JsonProperty("customer_facing_business_name")
    private String customer_facing_business_name;
    @JsonProperty("business_type")
    private String business_type;
    @JsonProperty("contact_name")
    private String contact_name;
    @JsonProperty("profile")
    private Profile profile;
    @JsonProperty("product_name")
    private String product_name;
    @JsonProperty("tnc")
    private TermsAndConditions tnc;
    @JsonProperty("last_published_at")
    private Integer last_published_at;
    @JsonProperty("stakeholder_id")
    private String stakeholder_id;
    private StakeholderResponseDto stakeholder;
    @JsonProperty("legal_info")
    private LegalInfo legal_info ;
    @JsonProperty("error")
    private RazorpayError error;
//    @JsonProperty("brand")
//    private Brand brand ;
////    @JsonProperty("notes")
////    private Notes notes ;
//    @JsonProperty("contact_info")
//    private ContactInfo contact_info ;
//    @JsonProperty("apps")
//    private  Apps apps ;
}