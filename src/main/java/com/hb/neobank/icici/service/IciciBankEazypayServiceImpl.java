package com.hb.neobank.icici.service;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.icici.common.IciciBankApiException;
import com.hb.neobank.icici.common.IciciConfig;

@Service
@Transactional
public class IciciBankEazypayServiceImpl implements IciciBankEazypayService {
	
	@Autowired
	private IciciConfig iciciConfig;

	@Override
	public ResponseDTO fetchMid(HashMap<String, String> apiReqData) throws Exception {
		String reqString = "";
		ResponseDTO response = new ResponseDTO();
		response.setPath("/icici-bank-eazypay");
		ObjectMapper mapper = new ObjectMapper();
		reqString = mapper.writeValueAsString(apiReqData);
		System.out.println("Callback packet : " + reqString);
		try { 
			if (reqString != null) {
				JSONObject currentJson = new JSONObject(reqString);
//				if (currentJson != null && (currentJson.has("Ezyparam1") )) {
				if (currentJson != null) {
					response.setStatus(200);
					response.setMessage("Callback packet fetched successfully");
				} 
//				else {
//					response.setStatus(400);
//					response.setMessage("Missing request parameter(s)");
//				}
			} else {
				response.setStatus(400);
				response.setMessage("Invalid Request");
			}
			
			return response;
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		throw new IciciBankApiException();
	}

	
}
