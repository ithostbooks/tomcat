package com.hb.neobank.razorpay.common;

import java.io.Serializable;

public class RazorpayApiException extends Exception implements Serializable {

	public static final long serialVersionUID = 1L;

	public RazorpayApiException() {
		super("Razor Pay api call went wrong");
	}
}
