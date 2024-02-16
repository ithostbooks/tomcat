package com.hb.neobank.icici.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.neobank.common.CommonFunctions;
import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.common.RestTemplateBuilder;
import com.hb.neobank.icici.common.IciciBankApiException;
import com.hb.neobank.icici.common.IciciConfig;
import com.hb.neobank.icici.common.IciciConst;
import com.hb.neobank.icici.dao.IciciAccOpeningReqDao;
import com.hb.neobank.icici.dao.IciciBankDao;
import com.hb.neobank.icici.model.IciciAccOpeningReqBO;
import com.hb.neobank.icici.model.IciciBankApiCallHistoryBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Transactional
public class IciciBankAccountAPIServiceImpl implements IciciBankAccountAPIService {

	private static final Logger logger = LoggerFactory.getLogger(IciciBankAccountAPIServiceImpl.class);

	@Autowired
	private IciciConfig iciciConfig;

	@Autowired
	private IciciBankDao iciciBankDao;

	@Autowired
	private IciciAccOpeningReqDao iciciAccOpeningReqDao;

	private MultiValueMap<String, String> ICICI_HEADERS;

	private void setHeader() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("Accept", "*/*");
		map.add("content-type", "application/json");
		this.ICICI_HEADERS = map;
	}

	@Override
	public ResponseDTO masterSearch(HashMap<String, String> apiReqData) throws IciciBankApiException {
		apiReqData.put("user", this.iciciConfig.getAccountOpeningUser());
		apiReqData.put("passCode", this.iciciConfig.getAccountOpeningPassCode());
		ResponseDTO response = this.iciciApiCall(this.iciciConfig.getAccountOpeningMasterAPI(), apiReqData,
				"ICICI Master Search");
		if (response.getStatus() == 200) {
			if (response.getData() != null) {
				IciciAccOpeningReqBO iciciAccOpeningReq = new IciciAccOpeningReqBO();
				if (response.getData().containsKey("reqString")) {
					iciciAccOpeningReq.setRequestData(response.getData().get("reqString").toString());
					response.getData().remove("reqString");
				}
				if (response.getData().containsKey("resString")) {
					iciciAccOpeningReq.setResponseData(response.getData().get("resString").toString());
					response.getData().remove("resString");
				}
			}
		}
		return response;
	}

	@Override
	public ResponseDTO registration(HashMap<String, Object> iciciBankAccReq) throws IciciBankApiException {
		iciciBankAccReq.put("user", this.iciciConfig.getAccountOpeningUser());
		iciciBankAccReq.put("passCode", this.iciciConfig.getAccountOpeningPassCode());
		ResponseDTO response = this.iciciApiCall(this.iciciConfig.getAccountOpeningRegistrationAPI(), iciciBankAccReq,
				"ICICI Account Registration");
		if (response.getStatus() == 200) {
			if (response.getData() != null) {
				IciciAccOpeningReqBO iciciAccOpeningReq = new IciciAccOpeningReqBO();
				if (response.getData().containsKey("reqString")) {
					iciciAccOpeningReq.setRequestData(response.getData().get("reqString").toString());
					response.getData().remove("reqString");
				}
				if (response.getData().containsKey("resString")) {
					iciciAccOpeningReq.setResponseData(response.getData().get("resString").toString());
					response.getData().remove("resString");
				}
				if (response.getData().containsKey("webUrl")) {
					iciciAccOpeningReq.setWebUrl(response.getData().get("webUrl").toString());
				}
				if (response.getData().containsKey("applicationNo")) {
					iciciAccOpeningReq.setApplicationNo(response.getData().get("applicationNo").toString());
				}
				if (response.getData().containsKey("trackerId")) {
					iciciAccOpeningReq.setTrackingNo(response.getData().get("trackerId").toString());
				}
				this.iciciAccOpeningReqDao.addIciciAccOpeningReq(iciciAccOpeningReq);
			}
		}
		return response;
	}

	private ResponseDTO iciciApiCall(String apiUrl, Object apiReqData, String reqType) throws IciciBankApiException {
		String reqString = "";
		ResponseDTO response = new ResponseDTO();
		response.setPath("/icici-bank-account");
		try {
			this.setHeader();
			RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
			ObjectMapper mapper = new ObjectMapper();
			reqString = mapper.writeValueAsString(apiReqData);
			HttpEntity<String> entity = new HttpEntity<>(reqString, this.ICICI_HEADERS);
			logger.error("Req Url  ****** :: " + apiUrl);
			ResponseEntity<String> responseEntity = restTemplateInstance.exchange(apiUrl, HttpMethod.POST, entity,
					String.class);
			String resString = responseEntity.getBody();
			logger.error("Response  ****** :: " + resString);
			this.addCallHistory(reqString, resString, reqType);
			response.putData("reqString", reqString);
			response.putData("resString", resString);
			if (resString != null) {
				JSONObject currentJson = new JSONObject(resString);
				if (currentJson != null && currentJson.has("status")) {
					response.setCode("ICICI_ACCOUNT");
					if (currentJson.has("message")) {
						response.setMessage(currentJson.getString("message"));
					}
					if (currentJson.getString("status").equalsIgnoreCase("success")) {
						response.setStatus(200);
					} else {
						response.setStatus(400);
					}
					this.fetchResponseData(currentJson, response);
				} else {
					throw new IciciBankApiException();
				}
			} else {
				throw new IciciBankApiException();
			}
			if (response.getMessage() != null && !response.getMessage().isEmpty()) {
				response.setMessage(CommonFunctions.toTitleCase(response.getMessage()));
			}
			return response;
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
			logger.error("Exception  ****** :: " + e.getMessage());
			this.addCallHistory(reqString, "Exception : " + e.getMessage(), reqType);
		}
		throw new IciciBankApiException();
	}

	private void addCallHistory(String reqData, String resData, String type) {
		IciciBankApiCallHistoryBO historyData = new IciciBankApiCallHistoryBO();
		historyData.setRequestData(reqData);
		historyData.setResponseData(resData);
		historyData.setRequestType(type);
		this.iciciBankDao.addApiCallHistory(historyData);
	}

	private void fetchResponseData(JSONObject responseJson, ResponseDTO returnResponse) {
		if (responseJson.has("totRetrievedrecords") && responseJson.getInt("totRetrievedrecords") > 0
				&& responseJson.has("srchRsltData") && !responseJson.isNull("srchRsltData")) {
			JSONArray jsonArray = responseJson.optJSONArray("srchRsltData");
			List<String> searchList = new ArrayList<>();
			if (jsonArray != null && jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					searchList.addAll(this.fetchAllSearchOptions(jsonArray.getJSONObject(i),
							responseJson.getInt("totRetrievedrecords")));
				}
			}
			returnResponse.putData("serachList", searchList);
		}
		if (responseJson.has("webUrl") && !responseJson.isNull("webUrl")) {
			returnResponse.putData("webUrl", responseJson.getString("webUrl"));
		}
		if (responseJson.has("p_APPLICATION_NO") && !responseJson.isNull("p_APPLICATION_NO")) {
			returnResponse.putData("applicationNo", responseJson.getString("p_APPLICATION_NO"));
		}
		if (responseJson.has("trackerId") && !responseJson.isNull("trackerId")) {
			returnResponse.putData("trackerId", responseJson.getString("trackerId"));
		}
	}

	private List<String> fetchAllSearchOptions(JSONObject jsonObject, int totRetrievedrecords) {
		List<String> searchList = new ArrayList<>();
		if (jsonObject.has("bcOptions") && !jsonObject.isNull("bcOptions")) {
			JSONObject bcOptionObject = jsonObject.getJSONObject("bcOptions");
			for (int i = 1; i <= totRetrievedrecords; i++) {
				String key = "option" + i;
				if (bcOptionObject.has(key) && !bcOptionObject.isNull(key)) {
					searchList.add(bcOptionObject.getString(key));
				}
			}
		}
		return searchList;
	}
}
