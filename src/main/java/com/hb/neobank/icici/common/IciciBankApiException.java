package com.hb.neobank.icici.common;

import java.io.Serializable;

public class IciciBankApiException extends Exception implements Serializable {

	public static final long serialVersionUID = 1L;

	public IciciBankApiException() {
		super("Icici Bank api call went wrong");

	}
}
