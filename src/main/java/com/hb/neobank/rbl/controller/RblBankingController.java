package com.hb.neobank.rbl.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.hb.neobank.rbl.service.RblBankingService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/rbl-banking")
public class RblBankingController {
	
	@Autowired
	RblBankingService rblBankingService;
	
	@RequestMapping(value = "/webhook", method = RequestMethod.POST)
	public ResponseEntity<?> getWebhook(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.rblBankingService.getCallBackResponse(apiReqData), HttpStatus.OK);
	}

}
