package com.hb.neobank.razorpay.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class KycDetails {
    private MultipartFile file;
    private Proof bank_proof;
}
