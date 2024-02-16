package com.hb.neobank.webhook.controller;

import com.hb.neobank.razorpay.common.RazorpayApiException;
import com.hb.neobank.razorpay.service.RazorpayPaymentLinkService;
import com.hb.neobank.razorpay.service.RazorpayPayoutService;
import com.hb.neobank.webhook.dto.RazorpayWebhookData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/neo-webhook")
public class NeoWebhookController {

    @Autowired
    RazorpayPayoutService razorPayPayoutService;

    @Autowired
    RazorpayPaymentLinkService razorpayPaymentLinkService;

    @RequestMapping(value = "/razorpay", method = RequestMethod.POST)
    public ResponseEntity<?> webhookUpdate(@RequestBody RazorpayWebhookData data) throws RazorpayApiException {
        this.razorPayPayoutService.webhookUpdate(data);
        return new ResponseEntity<String>("Done", HttpStatus.OK);
    }

    @RequestMapping(value = "/razorpay/payment", method = RequestMethod.POST)
    public ResponseEntity<?> paymentWebhook(@RequestBody RazorpayWebhookData data) throws RazorpayApiException {
        this.razorpayPaymentLinkService.paymentWebhook(data);
        return new ResponseEntity<String>("Done", HttpStatus.OK);
    }

    @RequestMapping(value = "/razorpay/account/status-update", method = RequestMethod.POST)
    public ResponseEntity<?> accountWebhook(@RequestBody RazorpayWebhookData data) throws RazorpayApiException {
        this.razorpayPaymentLinkService.accountWebhook(data);
        return new ResponseEntity<String>("Done", HttpStatus.OK);
    }
}
