package com.hb.neobank.razorpay.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.dto.AccountDto;
import com.hb.neobank.razorpay.dto.ProductConfig.ProductConfigResponseTO;
import com.hb.neobank.razorpay.dto.ProductConfig.ProductConfigTO;
import com.hb.neobank.razorpay.dto.SubMerchantTO;
import com.hb.neobank.razorpay.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v2")
public class AccountApiController {

    @Autowired
    private AccountService accountService;

    private ResponseDTO responseDTO;


    @GetMapping("/test")
    public String getSubMerchantById() throws Exception {
        return "abc";
    }


    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO addSubMerchantAccount(@RequestBody AccountDto accountDto) throws Exception {
        return this.accountService.addSubmerchantAccount(accountDto);
    }

    @RequestMapping(value = "/getAccountsList", method = RequestMethod.POST, name = "Account Listing->MSO")
    public ResponseEntity<?> getAccountList(@RequestBody HashMap<String, Integer> apiReqData) throws Exception {
        return new ResponseEntity<>(this.accountService.getSubMerchantList(apiReqData), HttpStatus.OK);
    }
    @RequestMapping(value = "/accounts/getAccountsListDefault", method = RequestMethod.GET, name = "Account Listing->MSO")
    public ResponseEntity<?> getAccountList() throws Exception {
        return new ResponseEntity<>(this.accountService.getSubMerchantList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{account_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSubMerchantById(@PathVariable("account_id") String id) throws Exception {
        return new ResponseEntity<>(this.accountService.getSubMerchantByIdDao(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{account_id}/documents", method = RequestMethod.GET)
    public ResponseEntity<?> getDocuments(@PathVariable("account_id") String id) throws Exception {
        return new ResponseEntity<>(this.accountService.getDocuments(id), HttpStatus.OK);
    }

    @RequestMapping(value = "accounts/{account_id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateSubMerchantById(@PathVariable("account_id") String id, @RequestBody AccountDto accountDto) throws Exception {
        return new ResponseEntity<>(this.accountService.updateSubmerchantAccount(id, accountDto), HttpStatus.OK);
    }
    //termsAndConditions

    @GetMapping(value = "/accounts/termsAndConditions")
    public ResponseEntity<?> getTermsAndConditions() throws Exception {
        return new ResponseEntity<>(this.accountService.getTermsAndConditions(), HttpStatus.OK);
    }

    @RequestMapping(value = "accounts/document", method = RequestMethod.POST)
    public ResponseEntity<?> uploadDocument(@RequestParam(name = "stakeholderId") String stakeholderId, @RequestParam(name = "subMerchantId") String subMerchantId, @RequestParam("document_type") String document_type, @RequestParam("url") String url, @RequestParam("file") MultipartFile file) throws RazorpayApiException, JsonProcessingException {
        ResponseDTO fileResponse = this.accountService.uploadDocument(stakeholderId, subMerchantId, document_type, file, url);
        return new ResponseEntity<>(fileResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{account_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSubMerchantById(@PathVariable("account_id") String id) throws Exception {
        SubMerchantTO subMerchant = this.accountService.deleteSubmerchantAccount(id);
        ResponseDTO response = ResponseDTO.responseBuilder(200, HttpStatus.OK.toString(), "Deleted Successfully", "/account", "subMerchant", subMerchant);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/addProductConfig", method = RequestMethod.POST)
    public ResponseEntity<?> addProductConfig(@RequestBody ProductConfigTO productConfig) throws Exception {
        return new ResponseEntity<>(this.accountService.addProductConfiguration(productConfig), HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/updateProductConfig", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateProductConfig(@RequestBody ProductConfigTO productConfig) throws Exception {
        return new ResponseEntity<>(this.accountService.updateProductConfiguration(productConfig), HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/getProductConfig/{account_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductConfig(@PathVariable(name = "account_id") String accountId) throws Exception {
        return new ResponseEntity<>(this.accountService.getProductConfiguration(accountId), HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/fetchProductConfig/{account_id}/productConfig/{product_id}", method = RequestMethod.GET)
    public ResponseEntity<?> fetchProductConfig(@PathVariable(name = "account_id") String accountId, @PathVariable(name = "product_id") String productId) throws Exception {
        ProductConfigResponseTO productConfigResponse = this.accountService.fetchProductConfiguration(accountId, productId);
        ResponseDTO response = ResponseDTO.responseBuilder(200, HttpStatus.OK.toString(), "fetched Successfully", "/account", "productConfig", productConfigResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
