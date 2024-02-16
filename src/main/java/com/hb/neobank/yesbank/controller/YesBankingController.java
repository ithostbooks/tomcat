package com.hb.neobank.yesbank.controller;

import com.hb.neobank.yesbank.service.YesBankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/yes-banking")
public class YesBankingController {

    @Autowired
    YesBankingService yesBankingService;

    @RequestMapping(value = "/txn-payment", method = RequestMethod.POST)
    public ResponseEntity<?> txnPayment(@RequestBody Object apiReqData) throws Exception {
        return new ResponseEntity<>(this.yesBankingService.txnPayment(apiReqData), HttpStatus.OK);
    }

    @RequestMapping(value = "/fund-confirmation", method = RequestMethod.POST)
    public ResponseEntity<?> fundConfirmation(@RequestBody Object apiReqData) throws Exception {
        return new ResponseEntity<>(this.yesBankingService.fundConfirmation(apiReqData), HttpStatus.OK);
    }

    @RequestMapping(value = "/fetch-payment-detail", method = RequestMethod.POST)
    public ResponseEntity<?> txnPaymentDetail(@RequestBody Object apiReqData) throws Exception {
        return new ResponseEntity<>(this.yesBankingService.txnPaymentDetail(apiReqData), HttpStatus.OK);
    }

    @RequestMapping(value = "/fetch-statement", method = RequestMethod.POST)
    public ResponseEntity<?> accountStatement(@RequestBody Object apiReqData) throws Exception {
        return new ResponseEntity<>(this.yesBankingService.accountStatement(apiReqData), HttpStatus.OK);
    }
}
