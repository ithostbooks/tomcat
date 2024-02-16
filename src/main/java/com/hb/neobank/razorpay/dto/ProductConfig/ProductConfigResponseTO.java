package com.hb.neobank.razorpay.dto.ProductConfig;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.hb.neobank.razorpay.dto.RazorpayError;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductConfigResponseTO {
    @JsonProperty("id")
    private String id;
    private String account_id;
    private Integer bankId;
    private String activation_status;
    private String product_name;
    private boolean tnc_accepted;
    private boolean tncFlag;
    private List<Requirements> requirements;
    private  ActiveConfigurationTO active_configuration;
    private TermsAndConditions tnc;
    private RazorpayError error;
}
