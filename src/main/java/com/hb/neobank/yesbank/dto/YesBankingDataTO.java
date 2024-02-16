package com.hb.neobank.yesbank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.Date;

public class YesBankingDataTO {
    @JsonSetter("ConsentId")
    private String consentId;

    @JsonSetter("TransactionIdentification")
    private String txnIdentification;

    @JsonSetter("Status")
    private String status;

    private Date creationDateTime;
    private Date statusUpdateDateTime;

    @JsonSetter("Initiation")
    private YesBankingInitiationTO initiationData;

    @JsonGetter("consentId")
    public String getConsentId() {
        return consentId;
    }

    @JsonGetter("txnIdentification")
    public String getTxnIdentification() {
        return txnIdentification;
    }

    @JsonGetter("status")
    public String getStatus() {
        return status;
    }

    @JsonGetter("creationDateTime")
    public Date getCreationDateTime() {
        return creationDateTime;
    }

    @JsonSetter("CreationDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    public void setCreationDateTime(Date creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @JsonGetter("statusUpdateDateTime")
    public Date getStatusUpdateDateTime() {
        return statusUpdateDateTime;
    }

    @JsonSetter("StatusUpdateDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    public void setStatusUpdateDateTime(Date statusUpdateDateTime) {
        this.statusUpdateDateTime = statusUpdateDateTime;
    }

    @JsonGetter("initiationData")
    public YesBankingInitiationTO getInitiationData() {
        return initiationData;
    }
}

class YesBankingInitiationTO {
    @JsonSetter("InstructionIdentification")
    private String instructionIdentification;

    @JsonSetter("EndToEndIdentification")
    private String endToEndIdentification;

    @JsonSetter("InstructedAmount")
    private YesBankingInstructedAmountTO instructedAmountData;

    @JsonSetter("DebtorAccount")
    private YesBankingDebtorAccountTO debtorAccountData;

    @JsonSetter("CreditorAccount")
    private YesBankingCreditorAccountTO creditorAccountData;

    @JsonSetter("RemittanceInformation")
    private YesBankingRemittanceInformationTO remittanceInformation;

    @JsonSetter("ClearingSystemIdentification")
    private String clearingSystemIdentification;

    @JsonGetter("instructionIdentification")
    public String getInstructionIdentification() {
        return instructionIdentification;
    }

    @JsonGetter("endToEndIdentification")
    public String getEndToEndIdentification() {
        return endToEndIdentification;
    }

    @JsonGetter("instructedAmountData")
    public YesBankingInstructedAmountTO getInstructedAmountData() {
        return instructedAmountData;
    }

    @JsonGetter("debtorAccountData")
    public YesBankingDebtorAccountTO getDebtorAccountData() {
        return debtorAccountData;
    }

    @JsonGetter("creditorAccountData")
    public YesBankingCreditorAccountTO getCreditorAccountData() {
        return creditorAccountData;
    }

    @JsonGetter("remittanceInformation")
    public YesBankingRemittanceInformationTO getRemittanceInformation() {
        return remittanceInformation;
    }

    @JsonGetter("clearingSystemIdentification")
    public String getClearingSystemIdentification() {
        return clearingSystemIdentification;
    }
}

class YesBankingInstructedAmountTO {
    private double amount;

    @JsonSetter("Currency")
    private String currencyCode;

    @JsonSetter("Amount")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @JsonGetter("amount")
    public double getAmount() {
        return amount;
    }

    @JsonGetter("currencyCode")
    public String getCurrencyCode() {
        return currencyCode;
    }
}

class YesBankingDebtorAccountTO {
    @JsonSetter("Identification")
    private String identification;

    @JsonSetter("SecondaryIdentification")
    private String secondaryIdentification;

    @JsonGetter("identification")
    public String getIdentification() {
        return identification;
    }

    @JsonGetter("secondaryIdentification")
    public String getSecondaryIdentification() {
        return secondaryIdentification;
    }
}

class YesBankingCreditorAccountTO {
    @JsonSetter("SchemeName")
    private String schemeName;

    @JsonSetter("Identification")
    private String identification;

    @JsonSetter("Name")
    private String name;

    @JsonSetter("BeneficiaryCode")
    private String beneficiaryCode;

    @JsonSetter("Unstructured")
    private YesBankingUnstructuredTO unstructuredData;

    @JsonSetter("RemittanceInformation")
    private YesBankingRemittanceInformationTO remittanceInformation;

    @JsonSetter("ClearingSystemIdentification")
    private String clearingSystemIdentification;

    @JsonGetter("schemeName")
    public String getSchemeName() {
        return schemeName;
    }

    @JsonGetter("identification")
    public String getIdentification() {
        return identification;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("beneficiaryCode")
    public String getBeneficiaryCode() {
        return beneficiaryCode;
    }

    @JsonGetter("unstructuredData")
    public YesBankingUnstructuredTO getUnstructuredData() {
        return unstructuredData;
    }

    @JsonGetter("remittanceInformation")
    public YesBankingRemittanceInformationTO getRemittanceInformation() {
        return remittanceInformation;
    }

    @JsonGetter("clearingSystemIdentification")
    public String getClearingSystemIdentification() {
        return clearingSystemIdentification;
    }
}

class YesBankingUnstructuredTO {
    @JsonSetter("ContactInformation")
    private YesBankingContactInformationTO contactInformation;

    @JsonGetter("contactInformation")
    public YesBankingContactInformationTO getContactInformation() {
        return contactInformation;
    }
}

class YesBankingContactInformationTO {
    @JsonSetter("EmailAddress")
    private String emailAddress;

    @JsonSetter("MobileNumber")
    private String mobileNumber;

    @JsonGetter("emailAddress")
    public String getEmailAddress() {
        return emailAddress;
    }

    @JsonGetter("mobileNumber")
    public String getMobileNumber() {
        return mobileNumber;
    }
}

class YesBankingRemittanceInformationTO {
    @JsonSetter("Reference")
    private String reference;

    @JsonSetter("Unstructured")
    YesBankingRemittanceUnstructuredTO unstructuredData;

    @JsonSetter("ClearingSystemIdentification")
    private String clearingSystemIdentification;

    @JsonGetter("reference")
    public String getReference() {
        return reference;
    }

    @JsonGetter("unstructuredData")
    public YesBankingRemittanceUnstructuredTO getUnstructuredData() {
        return unstructuredData;
    }

    @JsonGetter("clearingSystemIdentification")
    public String getClearingSystemIdentification() {
        return clearingSystemIdentification;
    }
}

class YesBankingRemittanceUnstructuredTO {
    @JsonSetter("CreditorReferenceInformation")
    private String creditorReferenceInformation;

    @JsonGetter("creditorReferenceInformation")
    public String getCreditorReferenceInformation() {
        return creditorReferenceInformation;
    }
}

