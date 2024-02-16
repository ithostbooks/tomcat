package com.hb.neobank.webhook.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentCard {
    public String id;
    public String entity;
    public String name;
    public String last4;
    public String network;
    public String type;
    public String issuer;
    public boolean international;
    public boolean emi;
    public String sub_type;
}
