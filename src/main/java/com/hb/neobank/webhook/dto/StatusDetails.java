package com.hb.neobank.webhook.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusDetails {
    private String description;
    private String source;
    private String reason;
}
