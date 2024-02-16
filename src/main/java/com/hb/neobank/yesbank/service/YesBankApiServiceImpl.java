package com.hb.neobank.yesbank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.common.RestTemplateBuilder;
import com.hb.neobank.yesbank.common.YesBankApiException;
import com.hb.neobank.yesbank.common.YesBankConfig;
import com.hb.neobank.yesbank.common.YesBankConst;
import com.hb.neobank.yesbank.dao.YesBankDao;
import com.hb.neobank.yesbank.dto.AdhocStatementResTO;
import com.hb.neobank.yesbank.dto.ResBodyTO;
import com.hb.neobank.yesbank.dto.YesBankingDataTO;
import com.hb.neobank.yesbank.dto.YesBankingResTO;
import com.hb.neobank.yesbank.model.YesBankApiCallHistoryBO;
import java.nio.charset.Charset;
import javax.transaction.Transactional;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class YesBankApiServiceImpl implements YesBankApiService {

    @Autowired
    private YesBankConfig yesBankConfig;

    @Autowired
    private YesBankDao yesBankDao;

    private MultiValueMap<String, String> YES_BANK_HEADERS;

    @Override
    public ResponseDTO txnPayment(Object apiReqData) throws YesBankApiException {
        return this.yesBankApiCall(this.yesBankConfig.getPaymentAPI(), apiReqData, false, "YES BANK ACCOUNT TXN PAYMENT");
    }

    @Override
    public ResponseDTO fundConfirmation(Object apiReqData) throws YesBankApiException {
        return this.yesBankApiCall(this.yesBankConfig.getFundConfirmationAPI(), apiReqData, false, "YES BANK ACCOUNT TXN FUN CONFIRMATION");
    }

    @Override
    public ResponseDTO txnPaymentDetail(Object apiReqData) throws YesBankApiException {
        return this.yesBankApiCall(this.yesBankConfig.getPaymentDetailAPI(), apiReqData, false, "YES BANK ACCOUNT TXN PAYMENT DETAIL");
    }

    @Override
    public ResponseDTO accountStatement(Object apiReqData) throws YesBankApiException {
        return this.yesBankApiCall(this.yesBankConfig.getAccountStatementAPI(), apiReqData, true, "YES BANK ACCOUNT STATEMENT");
    }

    private ResponseDTO yesBankApiCall(String apiUrl, Object apiReqData, boolean isStatement, String reqType)
            throws YesBankApiException {
        this.setHeader(isStatement);
        String reqString = "";
        ResponseDTO response = new ResponseDTO();
        response.setPath("/yesbank-banking");
        response.setStatus(400);
        response.setCode("YES_BANK");
        try {
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            ObjectMapper mapper = new ObjectMapper();
            reqString = mapper.writeValueAsString(apiReqData);

            HttpEntity<String> entity = new HttpEntity<>(reqString, this.YES_BANK_HEADERS);
            ResponseEntity<String> responseEntity = restTemplateInstance.exchange(this.yesBankConfig.getBaseUrlAPI() + apiUrl, HttpMethod.POST, entity, String.class);
            String resString = responseEntity.getBody();
            this.addCallHistory(reqString, resString, reqType);
            if (resString != null) {
                JSONObject currentJson = new JSONObject(resString);
                if (currentJson != null) {
                    if (isStatement) {
                        if (currentJson.has(YesBankConst.BANK_RESPONSE_KEY.ADHOC_STATEMENT_RES_KEY)) {
                            AdhocStatementResTO statementRes = new ObjectMapper().readValue(currentJson.get(YesBankConst.BANK_RESPONSE_KEY.ADHOC_STATEMENT_RES_KEY).toString(), AdhocStatementResTO.class);
                            response.putData(YesBankConst.RESPONSE_KEY.STATEMENT_RES_KEY, statementRes);
                            if (statementRes != null && statementRes.getResBody() != null) {
                                ResBodyTO resBody = statementRes.getResBody();
                                if (resBody.getErrorCode() == 0) {
                                    response.setStatus(200);
                                }
                                String errorMsg = "";
                                if (resBody.getErrorReason() != null && !resBody.getErrorReason().isEmpty()) {
                                    errorMsg += resBody.getErrorReason() + ". ";
                                }
                                if (resBody.getErrorMsg() != null && !resBody.getErrorMsg().isEmpty()) {
                                    errorMsg += resBody.getErrorMsg();
                                }
                                response.setMessage(errorMsg);
                            }
                        }
                    }
                    else {
                        YesBankingResTO yesBankingRes = new ObjectMapper().readValue(currentJson.toString(), YesBankingResTO.class);
                        response.putData(YesBankConst.RESPONSE_KEY.BANKING_RES_KEY, yesBankingRes);
                        if (yesBankingRes.getResData() != null) {
                            YesBankingDataTO resData = yesBankingRes.getResData();
                            if (resData.getStatus() != null) {
                                if (resData.getStatus().equalsIgnoreCase(YesBankConst.STATUS_CODE.RECEIVED) ||
                                        resData.getStatus().equalsIgnoreCase(YesBankConst.STATUS_CODE.SETTLEMENT_COMPLETED)) {
                                    response.setStatus(200);
                                }
                            }
                            else {
                                response.setStatus(200);
                            }
                        }
                        else {
                            String errorMsg = yesBankingRes.getErrorMessage();
                            if (errorMsg == null) {
                                errorMsg = yesBankingRes.getActionDes();
                            }
                            response.setMessage(errorMsg);
                        }
                        if (yesBankingRes.getMetaData() != null) {
                            String errorMsg = yesBankingRes.getMetaData().getErrorMessage();
                            if (errorMsg == null) {
                                errorMsg = yesBankingRes.getMetaData().getActionDes();
                            }
                            response.setMessage(errorMsg);
                        }
                    }
                }
                else {
                    throw new YesBankApiException();
                }
            }
            else {
                throw new YesBankApiException();
            }
            return response;
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            this.addCallHistory(reqString, "Exception : " + e.getMessage(), reqType);
        }
        throw new YesBankApiException();
    }

    private void setHeader(boolean isStatement) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Accept", "*/*");
        map.add("content-type", "application/json");
        map.add(YesBankConst.CLIENT_ID, this.yesBankConfig.getClientId());
        map.add(YesBankConst.CLIENT_SECRET, this.yesBankConfig.getClientSecret());
        String auth = this.yesBankConfig.getFt3UserName() + ":" + this.yesBankConfig.getFt3UserPassword();
        if (isStatement) {
            auth = this.yesBankConfig.getAdhocUserName() + ":" + this.yesBankConfig.getAdhocUserPassword();
        }
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        map.add(YesBankConst.AUTH, authHeader);
        this.YES_BANK_HEADERS = map;
    }

    private void addCallHistory(String reqData, String resData, String type) {
        YesBankApiCallHistoryBO historyData = new YesBankApiCallHistoryBO();
        historyData.setRequestData(reqData);
        historyData.setResponseData(resData);
        historyData.setRequestType(type);
        this.yesBankDao.addApiCallHistory(historyData);
    }
}
