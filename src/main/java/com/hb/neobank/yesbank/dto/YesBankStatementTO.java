package com.hb.neobank.yesbank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.Date;

public class YesBankStatementTO {
    private Date txnDate;

    @JsonSetter("txn_desc")
    private String txnDesc;

    private Date postDate;
    private double debitAmount;
    private double creditAmount;

    @JsonSetter("ref_chq_num")
    private String refChqNum;

    private double accountBalance;

    @JsonSetter("cod_txn_literal")
    private String codTxnLiteral;

    @JsonSetter("ref_usr_no")
    private String refUsrNo;

    @JsonSetter("txn_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    @JsonGetter("txnDate")
    public Date getTxnDate() {
        return txnDate;
    }

    @JsonGetter("txnDesc")
    public String getTxnDesc() {
        return txnDesc;
    }

    @JsonSetter("post_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @JsonGetter("postDate")
    public Date getPostDate() {
        return postDate;
    }

    @JsonSetter("amt_withdrawal")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }

    @JsonSetter("amt_deposit")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    @JsonGetter("debitAmount")
    public double getDebitAmount() {
        return debitAmount;
    }

    @JsonGetter("creditAmount")
    public double getCreditAmount() {
        return creditAmount;
    }

    @JsonGetter("refChqNum")
    public String getRefChqNum() {
        return refChqNum;
    }

    @JsonSetter("balance")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @JsonGetter("accountBalance")
    public double getBalance() {
        return accountBalance;
    }

    @JsonGetter("codTxnLiteral")
    public String getCodTxnLiteral() {
        return codTxnLiteral;
    }

    @JsonGetter("refUsrNo")
    public String getRefUsrNo() {
        return refUsrNo;
    }
}
