package com.hb.neobank.razorpay.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.hb.neobank.common.RestTemplateBuilder;
import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.common.RazorpayConfig;
import com.hb.neobank.razorpay.common.RazorpayConst;
import com.hb.neobank.razorpay.dao.RazorpayDao;
import com.hb.neobank.razorpay.dto.RazorpayPaymentLinkReqTO;
import com.hb.neobank.razorpay.dto.RazorpayPaymentLinkResponseTO;
import com.hb.neobank.razorpay.dto.RazorpayReqResTO;
import com.hb.neobank.razorpay.model.RazorpayApiCallHistoryBO;
import com.hb.neobank.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Base64;

@Service
@Transactional
public class RazorpayApiServiceImpl implements RazorpayApiService {

    @Autowired
    private RazorpayConfig razorpayConfig;

    @Autowired
    private RazorpayDao razorpayDao;

    @Autowired
    private ModelMapper modelMapper;

    private MultiValueMap<String, String> RAZORPAY_HEADERS;

    @Override
    public RazorpayReqResTO processPayment(RazorpayReqResTO razorpayPayout, String keyId, String keySecret) throws RazorpayApiException {
        return this.razorpayApiCall(this.razorpayConfig.getPayoutAPI(), razorpayPayout, null, null, HttpMethod.POST, keyId, keySecret);
    }

    @Override
    public RazorpayReqResTO checkPayoutStatus(String payoutId, String keyId, String keySecret) throws RazorpayApiException {
        return this.razorpayApiCall(this.razorpayConfig.getPayoutAPI(), null, payoutId, null, HttpMethod.GET, keyId, keySecret);
    }

    @Override
    public RazorpayReqResTO fetchTransactions(String accountNumber, String keyId, String keySecret, Long fromDate, Long toDate, Integer count, Integer skip) throws RazorpayApiException {
        StringBuilder queryParam = new StringBuilder("account_number=" + accountNumber);
        if (fromDate != null) {
            queryParam.append("&from=" + fromDate);
        }
        if (toDate != null) {
            queryParam.append("&to=" + toDate);
        }
        if (count != null) {
            queryParam.append("&count=" + count);
        }
        if (skip != null) {
            queryParam.append("&skip=" + skip);
        }
        return this.razorpayApiCall(this.razorpayConfig.getTransactionsAPI(), null, null, queryParam.toString(), HttpMethod.GET, keyId, keySecret);
    }

    @Override
    public RazorpayPaymentLinkResponseTO createPaymentLink(RazorpayPaymentLinkReqTO razorpayPaymentLinkReq, String subMerchantAccId) {
        return this.callRazorpayPaymentLinkApi(this.razorpayConfig.getPaymentLinksAPI(), razorpayPaymentLinkReq, HttpMethod.POST, subMerchantAccId);
    }

    @Override
    public RazorpayPaymentLinkResponseTO cancelPaymentLink(String id, String subMerchantAccId) {
        return this.callRazorpayPaymentLinkApi(this.razorpayConfig.getPaymentLinksAPI() + "/" + id + "/" + this.razorpayConfig.getCancelPaymentLinksAPI(),
                null, HttpMethod.POST, subMerchantAccId);
    }

    @Override
    public RazorpayPaymentLinkResponseTO fetchPaymentLink(String id, String subMerchantAccId) {
        if (id == null) {
            return this.callRazorpayPaymentLinkApi(this.razorpayConfig.getPaymentLinksAPI(), null, HttpMethod.GET, subMerchantAccId);
        } else {
            return this.callRazorpayPaymentLinkApi(this.razorpayConfig.getPaymentLinksAPI() + "/" + id, null, HttpMethod.GET, subMerchantAccId);
        }
    }


    private void setHeader(String keyId, String keySecret, String subMerchantAccId) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Accept", "*/*");
        map.add("content-type", "application/json");
        map.add("X-Razorpay-Account", subMerchantAccId);

        String valueToEncode;
        if (CommonUtil.checkNullEmpty(keyId) && CommonUtil.checkNullEmpty(keySecret)) {
            valueToEncode = keyId + ":" + keySecret;
        } else {
            valueToEncode = this.razorpayConfig.getKeyId() + ":" + this.razorpayConfig.getKeySecret();
        }
        String authHeader = "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
        map.add(RazorpayConst.AUTH_HAEDER, authHeader);
        this.RAZORPAY_HEADERS = map;
    }

    private RazorpayReqResTO razorpayApiCall(String apiUrl, Object apiReqData, String pathValue, String paramValue, HttpMethod reqType, String keyId, String keySecret) throws RazorpayApiException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        String reqUrl = this.razorpayConfig.getBaseUrlAPI() + apiUrl;
        if (pathValue != null && !pathValue.isEmpty()) {
            reqUrl += "/" + pathValue;
        }
        if (paramValue != null && !paramValue.isEmpty()) {
            reqUrl += "?" + paramValue;
        }
        String reqString = "";
        RazorpayReqResTO res = null;
        try {
            this.setHeader(keyId, keySecret, null);
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            reqString = mapper.writeValueAsString(apiReqData);
            HttpEntity<String> entity = new HttpEntity<>(reqString, this.RAZORPAY_HEADERS);
            ResponseEntity<String> responseEntity = restTemplateInstance.exchange(reqUrl, reqType, entity, String.class);
            String resString = responseEntity.getBody();
            this.addCallHistory(reqUrl, reqType.toString(), reqString, resString);
            if (resString != null) {
                modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
                res = modelMapper.map(mapper.readValue(resString, Object.class), RazorpayReqResTO.class);
            }
        } catch (HttpStatusCodeException restExp) {
            if (restExp.getResponseBodyAsString() != null) {
                modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
                try {
                    res = modelMapper.map(mapper.readValue(restExp.getResponseBodyAsString(), Object.class), RazorpayReqResTO.class);
                } catch (JsonProcessingException e) {
                    this.addCallHistory(reqUrl, reqType.toString() + " | " + restExp.getStatusCode().toString(), reqString, "Exception : " + e.getMessage());
                }
                this.addCallHistory(reqUrl, reqType.toString() + " | " + restExp.getStatusCode().toString(), reqString, restExp.getResponseBodyAsString());
            }
        } catch (Exception e) {
            this.addCallHistory(reqUrl, reqType.toString(), reqString, "Exception : " + e.getMessage());
        }
        if (res == null) {
            throw new RazorpayApiException();
        }
        return res;
    }

    private RazorpayPaymentLinkResponseTO callRazorpayPaymentLinkApi(String url, RazorpayPaymentLinkReqTO reqBody, HttpMethod methodType, String subMerchantAccId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        String reqUrl = razorpayConfig.getBaseUrlAPI() + url;
        String reqString = "";
        RazorpayPaymentLinkResponseTO res = null;
        try {
            this.setHeader(null, null, subMerchantAccId);
            RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
            reqString = mapper.writeValueAsString(reqBody);
            HttpEntity<String> entity = new HttpEntity<>(reqString, this.RAZORPAY_HEADERS);
            ResponseEntity<String> responseEntity = restTemplateInstance.exchange(reqUrl, methodType, entity, String.class);
            String resString = responseEntity.getBody();
            this.addCallHistory(reqUrl, methodType.toString(), reqString, resString);
            if (resString != null) {
                modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
                res = modelMapper.map(mapper.readValue(resString, Object.class), RazorpayPaymentLinkResponseTO.class);
                res.setResponseStatus(responseEntity.getStatusCodeValue());
            }
        } catch (HttpStatusCodeException restExp) {
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
            try {
                res = modelMapper.map(mapper.readValue(restExp.getResponseBodyAsString(), Object.class), RazorpayPaymentLinkResponseTO.class);
            } catch (JsonProcessingException e) {
                this.addCallHistory(reqUrl, methodType.toString() + " | " + restExp.getStatusCode(), reqString, "Exception : " + e.getMessage());
            }
            this.addCallHistory(reqUrl, methodType.toString() + " | " + restExp.getStatusCode(), reqString, restExp.getResponseBodyAsString());
        } catch (Exception e) {
            this.addCallHistory(reqUrl, methodType.toString(), reqString, "Exception : " + e.getMessage());
        }
        return res;
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
