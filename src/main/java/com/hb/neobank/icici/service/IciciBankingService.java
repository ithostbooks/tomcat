package com.hb.neobank.icici.service;

import java.util.HashMap;

import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.icici.common.IciciBankApiException;

public interface IciciBankingService {

	ResponseDTO linkAccount(HashMap<String, String> apiReqData) throws IciciBankApiException;

	ResponseDTO checkStatus(HashMap<String, String> apiReqData) throws IciciBankApiException;

	ResponseDTO deRegistration(HashMap<String, String> apiReqData) throws IciciBankApiException;

	ResponseDTO reRegistration(HashMap<String, String> apiReqData) throws IciciBankApiException;

	ResponseDTO fetchBalance(HashMap<String, String> apiReqData) throws IciciBankApiException;

	ResponseDTO fetchStatement(HashMap<String, String> apiReqData) throws IciciBankApiException;

	ResponseDTO txnOtpGeneration(HashMap<String, String> apiReqData) throws IciciBankApiException;

	ResponseDTO txnPayment(HashMap<String, String> apiReqData) throws IciciBankApiException;

	ResponseDTO txnStatus(HashMap<String, String> apiReqData) throws IciciBankApiException;
}
