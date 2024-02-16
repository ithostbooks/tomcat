package com.hb.neobank.rbl.service;

import java.util.HashMap;

import com.hb.neobank.common.RblResponseDTO;

public interface RblBankingService {
	
	public RblResponseDTO getCallBackResponse(HashMap<String, String> apiReqData) throws Exception;

}
