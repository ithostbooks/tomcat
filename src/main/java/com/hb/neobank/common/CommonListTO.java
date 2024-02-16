package com.hb.neobank.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommonListTO<T> {

    @JsonIgnore
    private Long totalRow;
    @JsonIgnore
    private Integer pageCount;

    private List<T> dataList = new ArrayList<>();

    private Double grandcreditAmount;

    private Double granddebitAmount;

}
