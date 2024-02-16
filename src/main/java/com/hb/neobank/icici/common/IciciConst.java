package com.hb.neobank.icici.common;

public class IciciConst {
	public static final String AGGR_NAME = "AGGRNAME";
	public static final String AGGR_ID = "AGGRID";
	public static final String CORP_ID = "CORPID";
	public static final String USER_ID = "USERID";
	public static final String URN = "URN";
	public static final String STATUS = "STATUS";

	public static final String UTR_NUMBER = "UTRNUMBER";
	public static final String REQ_ID = "REQID";
	public static final String EFFECTIVE_BAL = "EFFECTIVEBAL";

	public static final String CON_FLG = "CONFLG";
	public static final String LAST_TR_ID = "LASTTRID";

	public static final String CUSTOMERINDUCED = "CUSTOMERINDUCED";

	public static class ACC_STATUS_RESPONSE {
		public static final String ACTIVE = "REGISTERED";
		public static final String PENDING = "PENDING FOR SELF APPROVAL";
		public static final String DE_ACTIVE = "DEREGISTERED";
		public static final String REJECTED = "REJECTED";
	}

	public static class TXN_STATUS_RESPONSE {
		public static final String SUCCESS = "SUCCESS";
		public static final String PENDING = "PENDING";
		public static final String PENDING_PROCESS = "PENDING FOR PROCESSING";
		public static final String DUPLICATE = "DUPLICATE";
		public static final String FAILURE = "FAILURE";
	}

	public static String fetchErrorMsg(String errorCode, String errorMsg) {
		String returnErrorMsg = errorMsg;
		switch (errorCode) {
			case "107889": {
				returnErrorMsg = "Invalid OTP";
				break;
			}
			case "994006": {
				returnErrorMsg = "Invalid OTP";
				break;
			}
		}
		return returnErrorMsg;
	}
}
