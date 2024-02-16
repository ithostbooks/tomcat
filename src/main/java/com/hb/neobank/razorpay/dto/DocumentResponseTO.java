package com.hb.neobank.razorpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hb.neobank.razorpay.dto.ProductConfig.DocumentResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DocumentResponseTO {
    private List<DocumentResponse> individual_proof_of_address;
    private List<DocumentResponse> business_proof_of_identification;
    private List<DocumentResponse> additional_documents;
    private List<DocumentResponse> individual_proof_of_identification;
    private RazorpayError error;
}
