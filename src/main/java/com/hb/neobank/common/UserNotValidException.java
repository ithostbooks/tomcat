package com.hb.neobank.common;

public class UserNotValidException extends Exception {

	public static final long serialVersionUID = 1L;

	public UserNotValidException() {
		super("Client not valid!");
	}
}
