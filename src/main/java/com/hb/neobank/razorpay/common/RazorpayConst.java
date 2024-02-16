package com.hb.neobank.razorpay.common;

import java.util.List;

public class RazorpayConst {
	public static final String AUTH_HAEDER = "Authorization";

	public static final String[] PAYMENT_MODE = {"NEFT", "RTGS", "IMPS", "card"};
	public static final String[] PAYMENT_PURPOSE = {"refund", "cashback", "payout", "salary", "utility bill", "vendor bill"};

	public static final String[] CONTACT_TYPE = {"vendor", "employee", "self"};

	public static class AccountTypes {
		public static final String BANK_ACCOUNT = "bank_account";
	}

	public static final String[] ACCOUNT_TYPE = {AccountTypes.BANK_ACCOUNT};
}
