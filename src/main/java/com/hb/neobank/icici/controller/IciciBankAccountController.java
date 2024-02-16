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

import com.hb.neobank.icici.service.IciciBankAccountAPIService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/icici-bank-account")
public class IciciBankAccountController {

	@Autowired
	IciciBankAccountAPIService iciciBankAccountAPIService;

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<?> masterSearch(@RequestBody HashMap<String, String> apiReqData) throws Exception {
		return new ResponseEntity<>(this.iciciBankAccountAPIService.masterSearch(apiReqData), HttpStatus.OK);
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> registration(@RequestBody HashMap<String, Object> iciciBankAccReq) throws Exception {
		return new ResponseEntity<>(this.iciciBankAccountAPIService.registration(iciciBankAccReq), HttpStatus.OK);
	}
}
