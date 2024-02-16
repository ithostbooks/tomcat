package com.hb.neobank.icici.service;

import java.util.HashMap;

import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.icici.common.IciciBankApiException;

public interface IciciBankEazypayService {
	
	public ResponseDTO fetchMid(HashMap<String, String> apiReqData) throws Exception;

}
