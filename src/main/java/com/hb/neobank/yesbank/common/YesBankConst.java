package com.hb.neobank.yesbank.common;

public class YesBankConst {
	public static final String CLIENT_ID = "X-IBM-Client-Id";
	public static final String CLIENT_SECRET = "X-IBM-Client-Secret";
	public static final String AUTH = "Authorization";

	public static class BANK_RESPONSE_KEY {
		public static final String ADHOC_STATEMENT_RES_KEY = "AdhocStatementRes";
	}

	public static class RESPONSE_KEY {
		public static final String STATEMENT_RES_KEY = "yesBankStatementRes";
		public static final String BANKING_RES_KEY = "yesBankingRes";
	}

	public static class STATUS_CODE {
		public static final String RECEIVED = "Received";
		public static final String FAILED = "Failed";
		public static final String NA = "NA";
		public static final String SETTLEMENT_COMPLETED = "SettlementCompleted";
	}
}
