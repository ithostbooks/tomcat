package com.hb.neobank.razorpay.controller;

import com.hb.neobank.common.ResponseDTO;
import com.hb.neobank.common.ValidationError;
import com.hb.neobank.common.ValidationErrorBuilder;
import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.dto.RazorpayPaymentLinkReqTO;
import com.hb.neobank.razorpay.dto.RazorpayPaymentLinkResponseTO;
import com.hb.neobank.razorpay.service.RazorpayPaymentLinkService;
import com.hb.neobank.razorpay.validator.RazorpayPaymentLinkValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/razorpay-payment-link")
public class RazorpayPaymentLinkController {

    @Autowired
    private RazorpayPaymentLinkValidator razorpayPaymentLinkValidator;
    @Autowired
    RazorpayPaymentLinkService razorpayPaymentLinkService;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(razorpayPaymentLinkValidator);
    }

    @RequestMapping(value = "/create-payment-link", method = RequestMethod.POST)
    public ResponseEntity<?> createPaymentLink(
            @Valid @RequestBody RazorpayPaymentLinkReqTO razorpayPaymentLinkReq, Errors result
            , @RequestParam(value = "subMerchantAccId") final String subMerchantAccId
    ) throws RazorpayApiException, ParseException {

        if (result.hasErrors()) {
            ValidationError validationError = ValidationErrorBuilder.fromBindingErrors(result);
            return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
        }
        ResponseDTO response = this.razorpayPaymentLinkService.createPaymentLink(razorpayPaymentLinkReq, subMerchantAccId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/cancel-payment-link/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deletePaymentLink(
            @PathVariable String id
            , @RequestParam(value = "subMerchantAccId") final String subMerchantAccId
    ) {
        ResponseDTO response = this.razorpayPaymentLinkService.cancelPaymentLink(id, subMerchantAccId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"/fetch-payment-link/{id}", "/fetch-payment-link"}, method = RequestMethod.GET)
    public ResponseEntity<?> fetchPaymentLink(
            @PathVariable(required = false) String id
            , @RequestParam(value = "subMerchantAccId") final String subMerchantAccId
    ) {
        ResponseDTO response = this.razorpayPaymentLinkService.fetchPaymentLink(id, subMerchantAccId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
