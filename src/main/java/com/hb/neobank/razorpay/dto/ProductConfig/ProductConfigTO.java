package com.hb.neobank.razorpay.dto.ProductConfig;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hb.neobank.razorpay.dto.ProductConfig.ActiveConfigurationTO;
import com.hb.neobank.razorpay.dto.ProductConfig.AllRequiredDocuments;
import com.hb.neobank.razorpay.dto.ProductConfig.SettlementsTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductConfigTO {
    private String id;
    private String account_id;
    private String product_name;
    private boolean tnc_accepted;
    private ActiveConfigurationTO active_configuration;
    private SettlementsTO settlements;
    private Integer bankId;
    private List<AllRequiredDocuments> allRequiredDocuments;
}
