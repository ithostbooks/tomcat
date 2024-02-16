package com.hb.neobank.icici.dto;

import java.util.Date;

import org.json.JSONObject;

import com.hb.neobank.common.CommonFunctions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IciciBankStatementTO {
	private Date valueDate;
	private String valueDateStr;
	private double amount;
	private String chequeNo;
	private Date txnDate;
	private String txnDateStr;
	private String remarks;
	private String txnUniqueId;
	private String transactionId;
	private String type;
	private double balance;

	public IciciBankStatementTO(JSONObject data) {
		if (data != null) {
			if (data.has("VALUEDATE") && !data.isNull("VALUEDATE")) {
				this.valueDateStr = data.getString("VALUEDATE");
				this.valueDate = CommonFunctions.convertDateStringToDate(data.getString("VALUEDATE"), "dd-MM-yyyy");
			}
			if (data.has("TXNDATE") && !data.isNull("TXNDATE")) {
				this.txnDateStr = data.getString("TXNDATE");
				this.txnDate = CommonFunctions.convertDateStringToDate(data.getString("TXNDATE"),
						"dd-MM-yyyy HH:mm:ss");
			}
			if (data.has("AMOUNT") && !data.isNull("AMOUNT")) {
				this.amount = Double.parseDouble(data.getString("AMOUNT").replace(",", ""));
			}
			if (data.has("CHEQUENO")) {
				this.chequeNo = data.getString("CHEQUENO");
			}
			if (data.has("REMARKS") && !data.isNull("REMARKS")) {
				this.remarks = data.getString("REMARKS");
				if (this.remarks != null) {
					String[] remarkList = this.remarks.split("/");
					for (String s : remarkList) {
						if (s.contains("HBTXN")) {
							String[] remarkList2 = s.split(" ");
							for (String value : remarkList2) {
								if (value.contains("HBTXN")) {
									this.txnUniqueId = value;
								}
							}
						}
					}
				}
			}
			if (data.has("TRANSACTIONID")) {
				this.transactionId = data.getString("TRANSACTIONID");
			}
			if (data.has("TYPE") && !data.isNull("TYPE")) {
				if (data.getString("TYPE").equals("CR")) {
					this.type = "CREDIT";
				} else {
					this.type = "DEBIT";
				}
			}
			if (data.has("BALANCE") && !data.isNull("BALANCE")) {
				this.balance = Double.parseDouble(data.getString("BALANCE").replace(",", ""));
			}
		}
	}
}
