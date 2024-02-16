package com.hb.neobank.webhook.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class RazorpayWebhookData{
    private String entity;
    private String account_id;
    private String event;
    private ArrayList<String> contains;
    private Payload payload;
    private int created_at;
}


