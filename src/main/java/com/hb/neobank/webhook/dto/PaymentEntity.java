package com.hb.neobank.webhook.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class PaymentEntity {
    public String id;
    public String entity;
    public Integer amount;
    public String currency;
    public String status;
    public String order_id;
    public Object invoice_id;
    public boolean international;
    public String method;
    public Integer amount_refunded;
    public Object refund_status;
    public boolean captured;
    public String description;
    public String card_id;
    public PaymentCard card;
    public String bank;
    public String wallet;
    public String vpa;
    public String email;
    public String contact;
    public ArrayList<Object> notes;
    public Integer fee;
    public Integer tax;
    public String error_code;
    public String error_description;
    public String error_source;
    public String error_step;
    public String error_reason;
    public PaymentAcquirerData acquirer_data;
    public Integer created_at;
    // Payment Captured
    public Integer base_amount;
    public Integer amount_transferred;
}
