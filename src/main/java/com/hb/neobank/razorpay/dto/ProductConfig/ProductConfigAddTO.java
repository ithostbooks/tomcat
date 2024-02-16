package com.hb.neobank.razorpay.dto.ProductConfig;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductConfigAddTO {
    private String product_name;
    private boolean tnc_accepted;
}
