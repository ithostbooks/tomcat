package com.hb.neobank.icici.service;

import java.util.HashMap;

import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.icici.common.IciciBankApiException;

public interface IciciBankAccountAPIService {

	ResponseDTO masterSearch(HashMap<String, String> apiReqData) throws IciciBankApiException;

	ResponseDTO registration(HashMap<String, Object> iciciBankAccReq) throws IciciBankApiException;
}
