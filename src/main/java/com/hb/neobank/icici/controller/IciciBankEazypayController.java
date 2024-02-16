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

import com.hb.neobank.icici.service.IciciBankEazypayService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/icici-bank-eazypay")
public class IciciBankEazypayController {
	
	@Autowired
	IciciBankEazypayService iciciBankEazypayService;
	
	@RequestMapping(value = "/webhook-mid", method = RequestMethod.POST)
	public ResponseEntity<?> fetchMidFromCallback(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.iciciBankEazypayService.fetchMid(apiReqData), HttpStatus.OK);
	}

}
