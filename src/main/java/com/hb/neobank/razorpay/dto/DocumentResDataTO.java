package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResDataTO {
    private String url;
    private String stakeholderId;
    private String subMerchantId;
    private String type;
}
