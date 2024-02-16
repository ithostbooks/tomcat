package com.hb.neobank.yesbank.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class YesBankingMetaTO {
    @JsonSetter("ErrorCode")
    private String errorCode;

    @JsonSetter("ErrorSeverity")
    private String errorMessage;

    @JsonSetter("ActionCode")
    private String actionCode;

    @JsonSetter("ActionDescription")
    private String actionDes;

    @JsonGetter("errorCode")
    public String getErrorCode() {
        return errorCode;
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
