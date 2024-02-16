package com.hb.neobank.webhook.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Entity {
    private String id;
    private String entity;
    private String fund_account_id;
    private Integer amount;
    private String currency;
    private List<String> notes;
    private Integer fees;
    private Integer tax;
    private String status;
    private String purpose;
    private String utr;
    private String mode;
    private String reference_id;
    private String narration;
    private String batch_id;
    private StatusDetails status_details;
    private Integer created_at;
    private String fee_type;
    private String failure_reason;
    private Object error;
}
