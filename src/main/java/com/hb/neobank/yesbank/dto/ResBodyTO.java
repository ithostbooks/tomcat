package com.hb.neobank.yesbank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.ArrayList;
import java.util.Date;

public class ResBodyTO {
    @JsonSetter("customer_id")
    private String customerId;

    @JsonSetter("cod_acct_no")
    private String codAcctNo;

    @JsonSetter("cust_name")
    private String customerName;

    private Date txnStartDate;
    private Date txnEndDate;
    private double openingBalance;
    private double closingBalance;

    @JsonSetter("debit_count")
    private int debitStatementCount;

    @JsonSetter("credit_count")
    private int creditStatementCount;

    private int errorCode;

    @JsonSetter("error_msg")
    private String errorMsg;

    @JsonSetter("error_reason")
    private String errorReason;

    @JsonSetter("statement")
    private ArrayList<YesBankStatementTO> statementList;

    @JsonGetter("customerId")
    public String getCustomerId() {
        return customerId;
    }

    @JsonGetter("codAcctNo")
    public String getCodAcctNo() {
        return codAcctNo;
    }

    @JsonGetter("customerName")
    public String getCustomerName() {
        return customerName;
    }

    @JsonSetter("txn_start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public void setTxnStartDate(Date txnStartDate) {
        this.txnStartDate = txnStartDate;
    }

    @JsonSetter("txn_end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public void setTxnEndDate(Date txnEndDate) {
        this.txnEndDate = txnEndDate;
    }

    @JsonSetter("opening_balance")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    @JsonSetter("closing_balance")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }

    @JsonGetter("txnStartDate")
    public Date getTxnStartDate() {
        return txnStartDate;
    }

    @JsonGetter("txnEndDate")
    public Date getTxnEndDate() {
        return txnEndDate;
    }

    @JsonGetter("openingBalance")
    public double getOpeningBalance() {
        return openingBalance;
    }

    @JsonGetter("closingBalance")
    public double getClosingBalance() {
        return closingBalance;
    }

    @JsonGetter("debitStatementCount")
    public int getDebitStatementCount() {
        return debitStatementCount;
    }

    @JsonGetter("creditStatementCount")
    public int getCreditStatementCount() {
        return creditStatementCount;
    }

    @JsonSetter("error_code")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonGetter("errorCode")
    public int getErrorCode() {
        return errorCode;
    }

    @JsonGetter("errorMsg")
    public String getErrorMsg() {
        return errorMsg;
    }

    @JsonGetter("errorReason")
    public String getErrorReason() {
        return errorReason;
    }

    @JsonGetter("statementList")
    public ArrayList<YesBankStatementTO> getStatementList() {
        return statementList;
    }
}

