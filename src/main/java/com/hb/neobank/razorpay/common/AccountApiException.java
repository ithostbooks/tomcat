package com.hb.neobank.razorpay.common;

import java.io.Serializable;

public class AccountApiException extends Exception implements Serializable {
    public static final long serialVersionUID = 1L;

    public AccountApiException() {
        super("Account api call went wrong");
    }
}
