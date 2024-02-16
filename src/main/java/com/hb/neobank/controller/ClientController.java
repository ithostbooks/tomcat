package com.hb.neobank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hb.neobank.dto.ClientDetailsTO;
import com.hb.neobank.service.ClientDetailService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/neobank/client")
public class ClientController {

	@Autowired
	ClientDetailService clientDetailService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> registrationClient(@RequestBody ClientDetailsTO clientDetails) {
		return new ResponseEntity<>(this.clientDetailService.addClient(clientDetails), HttpStatus.OK);
	}

	@RequestMapping(value = "/activate", method = RequestMethod.POST)
	public ResponseEntity<?> activateClient(@RequestBody ClientDetailsTO clientDetails) {
		return new ResponseEntity<>(this.clientDetailService.activateClient(clientDetails), HttpStatus.OK);
	}

	@RequestMapping(value = "/deactivate", method = RequestMethod.POST)
	public ResponseEntity<?> deActivateClient(@RequestBody ClientDetailsTO clientDetails) {
		return new ResponseEntity<>(this.clientDetailService.deActivateClient(clientDetails), HttpStatus.OK);
	}

	@RequestMapping(value = "/resetReqLimit/{reqLimit}", method = RequestMethod.POST)
	public ResponseEntity<?> resetReqLimit(@RequestBody ClientDetailsTO clientDetails,
			@PathVariable("reqLimit") Integer reqLimit) {
		return new ResponseEntity<>(this.clientDetailService.resetReqLimit(clientDetails, reqLimit), HttpStatus.OK);
	}
}
