package com.hb.neobank.icici.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.hb.neobank.icici.common.IciciBankEncryption;
import com.hb.neobank.icici.common.IciciConfig;
import com.hb.neobank.icici.common.IciciConst;
import com.hb.neobank.icici.dao.IciciBankDao;
import com.hb.neobank.icici.dto.IciciBankStatementTO;
import com.hb.neobank.icici.model.IciciBankApiCallHistoryBO;

@Service
@Transactional
public class IciciBankApiServiceImpl implements IciciBankApiService {

	private static final Logger logger = LoggerFactory.getLogger(IciciBankAccountAPIServiceImpl.class);
	@Autowired
	private IciciConfig iciciConfig;

	@Autowired
	private IciciBankDao iciciBankDao;

	private MultiValueMap<String, String> ICICI_HEADERS;

	private void setHeaders(HashMap<String, String> apiReqData) {
		apiReqData.put(IciciConst.AGGR_NAME, this.iciciConfig.getAGGRNAME());
		apiReqData.put(IciciConst.AGGR_ID, this.iciciConfig.getAGGRID());
	}

	@Override
	public ResponseDTO linkAccount(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.setHeaders(apiReqData);
		return this.iciciApiCall(this.iciciConfig.getRegistrationAPI(), apiReqData, "LINK ICICI ACCOUNT");
	}

	@Override
	public ResponseDTO checkStatus(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.setHeaders(apiReqData);
		return this.iciciApiCall(this.iciciConfig.getRegistrationStatusAPI(), apiReqData, "CHECK ACCOUNT STATUS");
	}

	@Override
	public ResponseDTO deRegistration(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.setHeaders(apiReqData);
		return this.iciciApiCall(this.iciciConfig.getDeRegistrationAPI(), apiReqData, "UNLINK ICICI ACCOUNT");
	}

	@Override
	public ResponseDTO reRegistration(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.setHeaders(apiReqData);
		return this.iciciApiCall(this.iciciConfig.getRegistrationAPI(), apiReqData, "RELINK ICICI ACCOUNT");
	}

	@Override
	public ResponseDTO fetchBalance(HashMap<String, String> apiReqData) throws IciciBankApiException {
		apiReqData.put(IciciConst.AGGR_ID, this.iciciConfig.getAGGRID());
		ResponseDTO response = this.iciciApiCall(this.iciciConfig.getAccountBalanceAPI(), apiReqData,
				"FETCH BALANCE ICICI ACCOUNT");
		if (response.getStatus() == 200) {
			response.setMessage("Balance Fetched");
		}
		return response;
	}

	@Override
	public ResponseDTO fetchStatement(HashMap<String, String> apiReqData) throws IciciBankApiException {
		apiReqData.put(IciciConst.AGGR_ID, this.iciciConfig.getAGGRID());
		if (!apiReqData.containsKey(IciciConst.LAST_TR_ID)) {
			apiReqData.put(IciciConst.LAST_TR_ID, "");
			apiReqData.put(IciciConst.CON_FLG, "N");
		} else if (apiReqData.get(IciciConst.LAST_TR_ID) == null || apiReqData.get(IciciConst.LAST_TR_ID).isEmpty()) {
			apiReqData.put(IciciConst.CON_FLG, "N");
		} else {
			apiReqData.put(IciciConst.CON_FLG, "Y");
		}
		ResponseDTO response = this.iciciStatementApiCall(this.iciciConfig.getAccountStatementAPI(), apiReqData,
				"FETCH STATEMENT ICICI ACCOUNT");
		if (response.getStatus() == 200) {
			response.setMessage("Statement Fetched");
		}
		return response;
	}

	@Override
	public ResponseDTO txnOtpGeneration(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.setHeaders(apiReqData);
		return this.iciciApiCall(this.iciciConfig.getTxnOtpGenerationAPI(), apiReqData,
				"OTP GENERATION ICICI ACCOUNT TXN");
	}

	@Override
	public ResponseDTO txnPayment(HashMap<String, String> apiReqData) throws IciciBankApiException {
		this.setHeaders(apiReqData);
		apiReqData.put(IciciConst.CUSTOMERINDUCED, "Y");
		return this.iciciApiCall(this.iciciConfig.getTxnPaymentAPI(), apiReqData, "ICICI ACCOUNT TXN PAYMENT");
	}

	@Override
	public ResponseDTO txnStatus(HashMap<String, String> apiReqData) throws IciciBankApiException {
		apiReqData.put(IciciConst.AGGR_ID, this.iciciConfig.getAGGRID());
		return this.iciciApiCall(this.iciciConfig.getTxnStatusAPI(), apiReqData, "CHECK TRANSACTION STATUS");
	}

	private void setHeader() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("Accept", "*/*");
		map.add("content-length", "684");
		map.add("content-type", "text/plain");
		map.add("apikey", this.iciciConfig.getApiKey());
		map.add("host", this.iciciConfig.getHostIP());
		map.add("x-forwarded-for", this.iciciConfig.getProxyIP());
		this.ICICI_HEADERS = map;
	}

	private ResponseDTO iciciApiCall(String apiUrl, HashMap<String, String> apiReqData, String reqType)
			throws IciciBankApiException {
		String reqString = "";
		ResponseDTO response = new ResponseDTO();
		response.setPath("/icici-banking");
		try {
			this.setHeader();
			RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
			ObjectMapper mapper = new ObjectMapper();
			reqString = mapper.writeValueAsString(apiReqData);
			HttpEntity<String> entity = new HttpEntity<>(IciciBankEncryption.encrypt(reqString), this.ICICI_HEADERS);
			logger.error("Req Url  ****** :: " + apiUrl);
			ResponseEntity<String> responseEntity = restTemplateInstance
					.exchange(this.iciciConfig.getBaseUrlAPI() + apiUrl, HttpMethod.POST, entity, String.class);
			String resStr = responseEntity.getBody();
			logger.error("Response  ****** :: " + resStr);
			String resString = IciciBankEncryption.decrypt(resStr);
			this.addCallHistory(reqString, resString, reqType);
			if (resString != null) {
				JSONObject currentJson = new JSONObject(resString);
				if (currentJson != null && (currentJson.has("RESPONSE") || currentJson.has("Response")
						|| currentJson.has("response") || currentJson.has("success"))) {
					if (currentJson.has("Message")) {
						response.setMessage(currentJson.getString("Message"));
					} else if (currentJson.has("message")) {
						response.setMessage(currentJson.getString("message"));
					} else if (currentJson.has("MESSAGE")) {
						response.setMessage(currentJson.getString("MESSAGE"));
					} else if (currentJson.has("Status")) {
						response.setMessage(currentJson.getString("Status"));
					} else if (currentJson.has("STATUS")) {
						response.setMessage(currentJson.getString("STATUS"));
					}
					response.setCode("ICICI");
					if (currentJson.has("success")) {
						if (currentJson.getBoolean("success")) {
							response.setStatus(200);
						} else {
							response.setStatus(400);
						}
					} else {
						if ((currentJson.has("RESPONSE")
								&& currentJson.getString("RESPONSE").equalsIgnoreCase("success"))
								|| (currentJson.has("Response")
										&& currentJson.getString("Response").equalsIgnoreCase("success"))
								|| (currentJson.has("response")
										&& currentJson.getString("response").equalsIgnoreCase("success"))) {
							response.setStatus(200);
						} else {
							response.setStatus(400);
						}
					}
					if (currentJson.has(IciciConst.UTR_NUMBER)) {
						response.putData(IciciConst.UTR_NUMBER, currentJson.getString(IciciConst.UTR_NUMBER));
					}
					if (currentJson.has(IciciConst.REQ_ID)) {
						response.putData(IciciConst.REQ_ID, currentJson.getString(IciciConst.REQ_ID));
					}
					if (currentJson.has(IciciConst.EFFECTIVE_BAL)) {
						response.putData(IciciConst.EFFECTIVE_BAL, currentJson.getString(IciciConst.EFFECTIVE_BAL));
					}
					if (currentJson.has("ERRORCODE") && !currentJson.isNull("ERRORCODE")) {
						response.setMessage(
								IciciConst.fetchErrorMsg(currentJson.getString("ERRORCODE"), response.getMessage()));
					} else if (response.getMessage() != null){
						response.setMessage(IciciConst.fetchErrorMsg(response.getMessage(), response.getMessage()));
					}
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

	private ResponseDTO iciciStatementApiCall(String apiUrl, HashMap<String, String> apiReqData, String reqType)
			throws IciciBankApiException {
		String reqString = "";
		ResponseDTO response = new ResponseDTO();
		response.setPath("/icici-banking");
		try {
			this.setHeader();
			RestTemplate restTemplateInstance = RestTemplateBuilder.getPubPostbackRestTemplate();
			ObjectMapper mapper = new ObjectMapper();
			reqString = mapper.writeValueAsString(apiReqData);
			HttpEntity<String> entity = new HttpEntity<>(IciciBankEncryption.encrypt(reqString), this.ICICI_HEADERS);
			ResponseEntity<String> responseEntity = restTemplateInstance
					.exchange(this.iciciConfig.getBaseUrlAPI() + apiUrl, HttpMethod.POST, entity, String.class);
			String resString = IciciBankEncryption.decryptStatement(responseEntity.getBody());
			this.addCallHistory(reqString, resString, reqType);
			if (resString != null) {
				JSONObject currentJson = new JSONObject(resString);
				if (currentJson != null && (currentJson.has("RESPONSE") || currentJson.has("Response")
						|| currentJson.has("response") || currentJson.has("success"))) {
					if (currentJson.has("Status")) {
						response.setMessage(currentJson.getString("Status"));
					} else if (currentJson.has("STATUS")) {
						response.setMessage(currentJson.getString("STATUS"));
					} else if (currentJson.has("Message")) {
						response.setMessage(currentJson.getString("Message"));
					} else if (currentJson.has("message")) {
						response.setMessage(currentJson.getString("message"));
					} else if (currentJson.has("MESSAGE")) {
						response.setMessage(currentJson.getString("MESSAGE"));
					}
					response.setCode("ICICI");
					if (currentJson.has("success")) {
						if (currentJson.getBoolean("success")) {
							response.setStatus(200);
						} else {
							response.setStatus(400);
						}
					} else {
						if ((currentJson.has("RESPONSE")
								&& currentJson.getString("RESPONSE").equalsIgnoreCase("success"))
								|| (currentJson.has("Response")
										&& currentJson.getString("Response").equalsIgnoreCase("success"))
								|| (currentJson.has("response")
										&& currentJson.getString("response").equalsIgnoreCase("success"))) {
							response.setStatus(200);
						} else {
							response.setStatus(400);
						}
					}
					if (currentJson.has("Record") && !currentJson.isNull("Record")) {
						JSONArray jsonArray = currentJson.optJSONArray("Record");
						List<IciciBankStatementTO> listdata = new ArrayList<>();
						if (jsonArray != null) {
							for (int i = 0; i < jsonArray.length(); i++) {
								listdata.add(new IciciBankStatementTO(jsonArray.getJSONObject(i)));
							}
						} else {
							JSONObject jsonObj = currentJson.optJSONObject("Record");
							if (jsonObj != null) {
								listdata.add(new IciciBankStatementTO(jsonObj));
							}
						}
						response.setIciciBankStatementList(listdata);
					}
					if (currentJson.has(IciciConst.LAST_TR_ID)) {
						response.putData(IciciConst.LAST_TR_ID, currentJson.getString(IciciConst.LAST_TR_ID));
					}
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
}
