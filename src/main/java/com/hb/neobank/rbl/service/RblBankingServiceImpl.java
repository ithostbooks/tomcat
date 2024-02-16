package com.hb.neobank.rbl.service;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.neobank.common.RblResponseDTO;

@Service
@Transactional
public class RblBankingServiceImpl implements RblBankingService {

	@Override
	public RblResponseDTO getCallBackResponse(HashMap<String, String> apiReqData) throws Exception {
		String reqString = "";
		RblResponseDTO response = new RblResponseDTO();
		response.setPath("/rbl-banking");
		ObjectMapper mapper = new ObjectMapper();
		reqString = mapper.writeValueAsString(apiReqData);
		System.out.println("Callback packet : " + reqString);
		try { 
			if (reqString != null) {
				JSONObject currentJson = new JSONObject(reqString);
				if (currentJson != null) {
					response.setStatus(200);
					response.setMessage("Callback packet fetched successfully");
				} 
			} else {
				response.setStatus(400);
				response.setMessage("Invalid Request");
			}
			
			return response;
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		throw new Exception();
	}
	

}
