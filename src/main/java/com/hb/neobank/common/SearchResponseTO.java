package com.hb.neobank.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Setter
@Getter
public class SearchResponseTO<T> implements Serializable {

    private static final long serialVersionUID = -3932831066096414182L;
    @JsonIgnore
    private int pageCount;
    @JsonIgnore
    private int totalRowCount;
    private List<T> list;
    private int counter;
    private double openBalancebank;
    private HashMap<?, ?> extraData;

    private Double grandcreditAmount;
    private Double granddebitAmount;
    private boolean lotNoVerified;
    private int skippedRowCount;
    private List<Integer> currSelectedList;
    private Date date;
}
