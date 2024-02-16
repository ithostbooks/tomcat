package com.hb.neobank.razorpay.dto.stakeholder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllStakeholdersResponseDto {

    @JsonProperty("entity")
    private String entity;
    @JsonProperty("items")
    private List<StakeholderResponseDto> Items;
    @JsonProperty("count")
    private String count;
}
