package com.hb.neobank.razorpay.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.razorpay.common.AccountApiException;
import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.dto.AccountDto;
import com.hb.neobank.razorpay.dto.ProductConfig.ProductConfigResponseTO;
import com.hb.neobank.razorpay.dto.ProductConfig.ProductConfigTO;
import com.hb.neobank.razorpay.dto.SubMerchantTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;


public interface AccountService {

    public SubMerchantTO getSubmerchantById(String id) throws AccountApiException;

    public ResponseDTO getSubMerchantByIdDao(String id) throws AccountApiException;

    public ResponseDTO getDocuments(String id) throws AccountApiException;


    public ResponseDTO addSubmerchantAccount(AccountDto accountDto) throws AccountApiException, JsonProcessingException;

    public ResponseDTO updateSubmerchantAccount(String id, AccountDto accountDto) throws AccountApiException, JsonProcessingException;

    public ResponseDTO uploadDocument(String stakeholderId, String subMerchantId, String document_type, MultipartFile file, String reqUrl) throws RazorpayApiException, JsonProcessingException;


    public SubMerchantTO deleteSubmerchantAccount(String id) throws AccountApiException;

    ResponseDTO getSubMerchantList(HashMap<String, Integer> apiReqData);

    ResponseDTO getTermsAndConditions() throws AccountApiException, JsonProcessingException;

    ResponseDTO addProductConfiguration(ProductConfigTO productConfig) throws AccountApiException, JsonProcessingException;

    ResponseDTO getProductConfiguration(String accountId) throws AccountApiException, JsonProcessingException;

    ProductConfigResponseTO fetchProductConfiguration(String accountId, String productId) throws AccountApiException, JsonProcessingException;


    ResponseDTO updateProductConfiguration(ProductConfigTO productConfig) throws Exception;

    ResponseDTO getSubMerchantList();
}
