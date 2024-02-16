package com.hb.neobank.yesbank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.Date;

public class AdhocStatementResTO {
    private ReqHeader reqHeader;
    private ResBodyTO resBody;

    @JsonProperty("reqHeader")
    public ReqHeader getReqHeader() {
        return reqHeader;
    }

    @JsonProperty("ReqHdr")
    public void setReqHeader(ReqHeader reqHeader) {
        this.reqHeader = reqHeader;
    }

    @JsonProperty("resBody")
    public ResBodyTO getResBody() {
        return resBody;
    }

    @JsonProperty("ResBody")
    public void setResBody(ResBodyTO resBody) {
        this.resBody = resBody;
    }
}

class ReqHeader {
    @JsonSetter("ConsumerContext")
    private ConsumerContext consumerContext;

    @JsonSetter("ServiceContext")
    private ServiceContext serviceContext;

    @JsonSetter("ServiceResponse")
    private ServiceResponse serviceResponse;

    @JsonGetter("consumerContext")
    public ConsumerContext getConsumerContext() {
        return consumerContext;
    }

    @JsonGetter("serviceContext")
    public ServiceContext getServiceContext() {
        return serviceContext;
    }

    @JsonGetter("serviceResponse")
    public ServiceResponse getServiceResponse() {
        return serviceResponse;
    }
}

class ConsumerContext {
    @JsonSetter("RequesterID")
    private String requesterID;

    @JsonGetter("requesterID")
    public String getRequesterID() {
        return requesterID;
    }
}

class ServiceContext {
    @JsonSetter("ServiceName")
    private String serviceName;

    @JsonSetter("ReqRefNum")
    private String reqRefNum;

    private Date reqRefTimeStamp;

    @JsonSetter("ServiceVersionNo")
    private String serviceVersionNo;

    @JsonGetter("serviceName")
    public String getServiceName() {
        return serviceName;
    }

    @JsonGetter("reqRefNum")
    public String getReqRefNum() {
        return reqRefNum;
    }

    @JsonSetter("ReqRefTimeStamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    public void setReqRefTimeStamp(Date reqRefTimeStamp) {
        this.reqRefTimeStamp = reqRefTimeStamp;
    }

    @JsonGetter("reqRefTimeStamp")
    public Date getReqRefTimeStamp() {
        return reqRefTimeStamp;
    }

    @JsonGetter("serviceVersionNo")
    public String getServiceVersionNo() {
        return serviceVersionNo;
    }
}

class ServiceResponse {
    private Date esbResTimeStamp;
    private int esbResStatus;

    @JsonGetter("esbResTimeStamp")
    public Date getEsbResTimeStamp() {
        return esbResTimeStamp;
    }

    @JsonSetter("EsbResTimeStamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    public void setEsbResTimeStamp(Date esbResTimeStamp) {
        this.esbResTimeStamp = esbResTimeStamp;
    }

    @JsonSetter("EsbResStatus")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public void setEsbResStatus(int esbResStatus) {
        this.esbResStatus = esbResStatus;
    }

    @JsonGetter("esbResStatus")
    public int getEsbResStatus() {
        return esbResStatus;
    }
}