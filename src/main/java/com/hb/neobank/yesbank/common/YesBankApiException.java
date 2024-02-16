package com.hb.neobank.yesbank.common;

import java.io.Serializable;

public class YesBankApiException extends Exception implements Serializable {

	public static final long serialVersionUID = 1L;

	public YesBankApiException() {
		super("Yes Bank api call went wrong");
	}
}
