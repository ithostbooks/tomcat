package com.hb.neobank.accouting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.neobank.accouting.common.HbAccountingConfig;
import com.hb.neobank.common.RestTemplateBuilder;
import com.hb.neobank.razorpay.dao.RazorpayDao;
import com.hb.neobank.razorpay.model.RazorpayApiCallHistoryBO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
@Transactional
public class HbAccountingApiServiceImpl implements HbAccountingApiService {

    @Autowired
    private HbAccountingConfig hbAccountingConfig;
    @Autowired
    private RazorpayDao razorpayDao;


    private MultiValueMap<String, String> HB_ACCOUNTING_HEADERS;

    @Override
    public boolean statusChangeCallBack(HashMap<String, String> razorpayWebhookData, String uuid) {
        return this.hbAccountingApiCall(this.hbAccountingConfig.getRazorpayWebhookAPI(), razorpayWebhookData, uuid, HttpMethod.POST);
    }

    @Override
    public boolean paymentStatusChangeCallBack(HashMap<String, String> razorpayWebhookData, String uuid) {
        return this.hbAccountingApiCall(this.hbAccountingConfig.getRazorpayWebhookPaymentAPI(), razorpayWebhookData, uuid, HttpMethod.POST);
    }

    private void setHeader() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Accept", "*/*");
        map.add("content-type", "application/json");
        this.HB_ACCOUNTING_HEADERS = map;
    }

    private boolean hbAccountingApiCall(String apiUrl, Object apiReqData, String pathValue, HttpMethod reqType) {
        ObjectMapper mapper = new ObjectMapper();
        String reqUrl = this.hbAccountingConfig.getBaseUrlAPI() + apiUrl;
        if (pathValue != null && !pathValue.isEmpty()) {
            reqUrl += "?companyUUID=" + pathValue;
        }
        String reqString = "";
        try {
            this.setHeader();
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            reqString = mapper.writeValueAsString(apiReqData);
            HttpEntity<String> entity = new HttpEntity<>(reqString, this.HB_ACCOUNTING_HEADERS);
            ResponseEntity<String> responseEntity = restTemplateInstance.exchange(reqUrl, reqType, entity, String.class);
            String resString = responseEntity.getBody();
            this.addCallHistory(reqUrl, reqType.toString(), reqString, resString);
            if (resString != null) {
                JSONObject currentJson = new JSONObject(resString);
                if (currentJson != null && currentJson.has("status") && currentJson.getInt("status") == 200) {
                    return true;
                }
            }
        } catch (Exception e) {
            this.addCallHistory(reqUrl, reqType.toString(), reqString, "Exception : " + e.getMessage());
        }
        return false;
    }

    private void addCallHistory(String reqUrl, String reqMethod, String reqData, String resData) {
        RazorpayApiCallHistoryBO historyData = new RazorpayApiCallHistoryBO();
        historyData.setRequestUrl(reqUrl);
        historyData.setRequestMethod(reqMethod);
        historyData.setRequestData(reqData);
        historyData.setResponseData(resData);
        this.razorpayDao.addApiCallHistory(historyData);
    }
}
