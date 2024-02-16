package com.hb.neobank.icici.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hb.neobank.icici.service.IciciBankingService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/icici-banking")
public class IciciBankingController {

	@Autowired
	IciciBankingService iciciBankingService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> linkAccount(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.iciciBankingService.linkAccount(apiReqData), HttpStatus.OK);
	}

	@RequestMapping(value = "/registration-status", method = RequestMethod.POST)
	public ResponseEntity<?> checkStatus(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.iciciBankingService.checkStatus(apiReqData), HttpStatus.OK);
	}

	@RequestMapping(value = "/de-registration", method = RequestMethod.POST)
	public ResponseEntity<?> deRegistration(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.iciciBankingService.deRegistration(apiReqData), HttpStatus.OK);
	}

	@RequestMapping(value = "/re-registration", method = RequestMethod.POST)
	public ResponseEntity<?> reRegistration(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.iciciBankingService.reRegistration(apiReqData), HttpStatus.OK);
	}

	@RequestMapping(value = "/fetch-balance", method = RequestMethod.POST)
	public ResponseEntity<?> fetchBalance(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.iciciBankingService.fetchBalance(apiReqData), HttpStatus.OK);
	}

	@RequestMapping(value = "/fetch-statement", method = RequestMethod.POST)
	public ResponseEntity<?> fetchStatement(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.iciciBankingService.fetchStatement(apiReqData), HttpStatus.OK);
	}

	@RequestMapping(value = "/txn-otp", method = RequestMethod.POST)
	public ResponseEntity<?> txnOtpGeneration(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.iciciBankingService.txnOtpGeneration(apiReqData), HttpStatus.OK);
	}

	@RequestMapping(value = "/txn-payment", method = RequestMethod.POST)
	public ResponseEntity<?> txnPayment(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.iciciBankingService.txnPayment(apiReqData), HttpStatus.OK);
	}

	@RequestMapping(value = "/txn-status", method = RequestMethod.POST)
	public ResponseEntity<?> txnStatus(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.iciciBankingService.txnStatus(apiReqData), HttpStatus.OK);
	}
}
