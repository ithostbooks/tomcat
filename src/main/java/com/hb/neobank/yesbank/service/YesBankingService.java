package com.hb.neobank.yesbank.service;

import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.yesbank.common.YesBankApiException;
import java.util.HashMap;
import org.json.JSONObject;

public interface YesBankingService {

    ResponseDTO txnPayment(Object apiReqData) throws YesBankApiException;

    ResponseDTO fundConfirmation(Object apiReqData) throws YesBankApiException;

    ResponseDTO txnPaymentDetail(Object apiReqData) throws YesBankApiException;

    ResponseDTO accountStatement(Object apiReqData) throws YesBankApiException;
}
