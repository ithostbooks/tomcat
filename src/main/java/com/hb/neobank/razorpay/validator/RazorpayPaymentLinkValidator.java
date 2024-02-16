package com.hb.neobank.razorpay.validator;

import com.hb.neobank.razorpay.common.RazorpayConst;
import com.hb.neobank.razorpay.controller.RazorpayPayoutController;
import com.hb.neobank.razorpay.dto.RazorpayPaymentLinkReqTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Arrays;

@ControllerAdvice(assignableTypes = RazorpayPayoutController.class)
public class RazorpayPaymentLinkValidator implements Validator {
    @Override
    public boolean supports(Class<?> paramClass) {
        return RazorpayPaymentLinkReqTO.class.equals(paramClass) ;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        RazorpayPaymentLinkReqTO razorpayPaymentLinkReqTO = (RazorpayPaymentLinkReqTO) obj;
        if (razorpayPaymentLinkReqTO != null) {

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "M", "Mandatory");
            if (razorpayPaymentLinkReqTO.getAmount() == null || razorpayPaymentLinkReqTO.getAmount() <= 0) {
                errors.rejectValue("amount", "I", "Invalid");
            } else {
                razorpayPaymentLinkReqTO.setAmount(razorpayPaymentLinkReqTO.getAmount() * 100);
            }

//            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "acceptPartial", "M", "Mandatory");
//            if ( razorpayPaymentLinkReqTO.getAcceptPartial() ) {
//                if(razorpayPaymentLinkReqTO.getFirstMinPartialAmount()!=null ){
//                    if(razorpayPaymentLinkReqTO.getFirstMinPartialAmount()<=0){
//                        errors.rejectValue("firstMinPartialAmount", "I", "Invalid");
//                    }
//                    else{
//                        razorpayPaymentLinkReqTO.setFirstMinPartialAmount(razorpayPaymentLinkReqTO.getFirstMinPartialAmount() * 100);
//                    }
//                }
//                else{
//                    razorpayPaymentLinkReqTO.setFirstMinPartialAmount(100.00);
//                }
//            }

            if(razorpayPaymentLinkReqTO.getCustomer()!=null){
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.name", "M", "Mandatory");
//                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.email", "M", "Mandatory");
//                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.contact", "M", "Mandatory");
            }

        }
    }
}
