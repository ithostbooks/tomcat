package com.hb.neobank.yesbank.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.ArrayList;
import java.util.Date;

public class YesBankingResTO {
    @JsonSetter("Data")
    private YesBankingDataTO resData;

    @JsonSetter("Risk")
    private YesBankingRiskTO riskData;

    @JsonSetter("Links")
    private YesBankingLinksTO linksData;

    @JsonSetter("Meta")
    private YesBankingMetaTO metaData;

    @JsonSetter("FundsAvailableResult")
    private FundAvailableResultTO fundAvailableResult;

    @JsonSetter("Code")
    private String errorCode;

    @JsonSetter("Id")
    private String id;

    @JsonSetter("Message")
    private String errorMessage;

    @JsonSetter("ActionCode")
    private String actionCode;

    @JsonSetter("ActionDescription")
    private String actionDes;

    @JsonGetter("resData")
    public YesBankingDataTO getResData() {
        return resData;
    }

    @JsonGetter("riskData")
    public YesBankingRiskTO getRiskData() {
        return riskData;
    }

    @JsonGetter("linksData")
    public YesBankingLinksTO getLinksData() {
        return linksData;
    }

    @JsonGetter("metaData")
    public YesBankingMetaTO getMetaData() {
        return metaData;
    }

    @JsonGetter("fundAvailableResult")
    public FundAvailableResultTO getFundAvailableResult() {
        return fundAvailableResult;
    }

    @JsonGetter("errorCode")
    public String getErrorCode() {
        return errorCode;
    }

    @JsonGetter("id")
    public String getId() {
        return id;
    }

    @JsonGetter("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonGetter("actionCode")
    public String getActionCode() {
        return actionCode;
    }

    @JsonGetter("actionDes")
    public String getActionDes() {
        return actionDes;
    }
}

class YesBankingRiskTO {
    @JsonSetter("PaymentContextCode")
    private String paymentContextCode;

    @JsonSetter("DeliveryAddress")
    private YesBankingDeliveryAddressTO deliveryAddress;

    @JsonGetter("paymentContextCode")
    public String getPaymentContextCode() {
        return paymentContextCode;
    }

    @JsonGetter("deliveryAddress")
    public YesBankingDeliveryAddressTO getDeliveryAddress() {
        return deliveryAddress;
    }
}

class YesBankingDeliveryAddressTO {
    @JsonSetter("AddressLine")
    private ArrayList<String> addressLineList;

    @JsonSetter("StreetName")
    private String streetName;

    @JsonSetter("BuildingNumber")
    private String buildingNumber;

    @JsonSetter("PostCode")
    private String postCode;

    @JsonSetter("TownName")
    private String townName;

    @JsonSetter("CountySubDivision")
    private ArrayList<String> countySubDivisionList;

    @JsonSetter("Country")
    private String country;

    @JsonGetter("addressLineList")
    public ArrayList<String> getAddressLineList() {
        return addressLineList;
    }

    @JsonGetter("streetName")
    public String getStreetName() {
        return streetName;
    }

    @JsonGetter("buildingNumber")
    public String getBuildingNumber() {
        return buildingNumber;
    }

    @JsonGetter("postCode")
    public String getPostCode() {
        return postCode;
    }

    @JsonGetter("townName")
    public String getTownName() {
        return townName;
    }

    @JsonGetter("countySubDivisionList")
    public ArrayList<String> getCountySubDivisionList() {
        return countySubDivisionList;
    }

    @JsonGetter("country")
    public String getCountry() {
        return country;
    }
}

class YesBankingLinksTO {
    @JsonSetter("Self")
    private String selfLink;

    @JsonGetter("selfLink")
    public String getSelfLink() {
        return selfLink;
    }
}

class FundAvailableResultTO {
    private Date fundsAvailableDateTime;

    @JsonSetter("FundsAvailable")
    private boolean fundsAvailableFlag;

    private double balanceAmount;

    @JsonGetter("fundsAvailableDateTime")
    public Date getFundsAvailableDateTime() {
        return fundsAvailableDateTime;
    }

    @JsonSetter("FundsAvailableDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    public void setFundsAvailableDateTime(Date fundsAvailableDateTime) {
        this.fundsAvailableDateTime = fundsAvailableDateTime;
    }

    @JsonGetter("fundsAvailableFlag")
    public boolean isFundsAvailableFlag() {
        return fundsAvailableFlag;
    }

    @JsonGetter("balanceAmount")
    public double getBalanceAmount() {
        return balanceAmount;
    }

    @JsonSetter("BalanceAmount")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}