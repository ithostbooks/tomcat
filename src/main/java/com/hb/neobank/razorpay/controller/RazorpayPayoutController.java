package com.hb.neobank.razorpay.controller;

import com.hb.neobank.common.ValidationError;
import com.hb.neobank.common.ValidationErrorBuilder;
import com.hb.neobank.razorpay.dto.RazorpayReqResTO;
import com.hb.neobank.razorpay.dto.RazorpayTxnReqTO;
import com.hb.neobank.razorpay.service.RazorpayPayoutService;
import com.hb.neobank.razorpay.validator.RazorpayPayoutValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/razorpay-payout")
public class RazorpayPayoutController {

    @Autowired
    private RazorpayPayoutValidator razorpayPayoutValidator;
    @Autowired
    RazorpayPayoutService razorPayPayoutService;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(razorpayPayoutValidator);
    }

    @RequestMapping(value = "/process-payment", method = RequestMethod.POST)
    public ResponseEntity<?> processPayment(@RequestParam(value = "keyId") final String keyId,
                                            @RequestParam(value = "keySecret") final String keySecret,
                                            @Valid @RequestBody RazorpayReqResTO razorpayPayout, Errors result) throws Exception {
        if (result.hasErrors()) {
            ValidationError validationError = ValidationErrorBuilder.fromBindingErrors(result);
            return new ResponseEntity<ValidationError>(validationError, HttpStatus.OK);
        }
        return new ResponseEntity<>(this.razorPayPayoutService.processPayment(razorpayPayout, keyId, keySecret), HttpStatus.OK);
    }

    @RequestMapping(value = "/payment-status/{payoutId}", method = RequestMethod.GET)
    public ResponseEntity<?> checkPayoutStatus(@PathVariable("payoutId") String payoutId,
                                               @RequestParam(value = "keyId") final String keyId,
                                               @RequestParam(value = "keySecret") final String keySecret) throws Exception {
        return new ResponseEntity<>(this.razorPayPayoutService.checkPayoutStatus(payoutId, keyId, keySecret), HttpStatus.OK);
    }

    @RequestMapping(value = "/transactions/{accountNumber}", method = RequestMethod.POST)
    public ResponseEntity<?> fetchTransactions(@PathVariable("accountNumber") String accountNumber,
                                               @RequestParam(value = "keyId") final String keyId,
                                               @RequestParam(value = "keySecret") final String keySecret,
                                               @RequestBody RazorpayTxnReqTO razorpayTxnReqTO) throws Exception {
        return new ResponseEntity<>(this.razorPayPayoutService.fetchTransactions(accountNumber, keyId, keySecret, razorpayTxnReqTO), HttpStatus.OK);
    }
}
