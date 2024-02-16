package com.hb.neobank.razorpay.common;

import java.io.Serializable;

public class StakeholderApiException extends Exception implements Serializable {

    public StakeholderApiException() {
        super("Stakeholder api call went wrong");
    }
}
