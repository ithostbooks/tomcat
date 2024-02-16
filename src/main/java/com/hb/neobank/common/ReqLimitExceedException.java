package com.hb.neobank.common;

public class ReqLimitExceedException extends Exception {

	public static final long serialVersionUID = 1L;

	public ReqLimitExceedException() {
		super("Req Limit Exceeded!");
	}
}
