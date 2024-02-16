package com.hb.neobank.razorpay.dto.ProductConfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AllRequiredDocuments {
    private String docType;
    private MultipartFile file;
    private String url;
}
