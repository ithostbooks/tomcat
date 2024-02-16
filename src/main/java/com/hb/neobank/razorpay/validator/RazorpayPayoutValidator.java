package com.hb.neobank.razorpay.validator;

import com.hb.neobank.razorpay.common.RazorpayConst;
import com.hb.neobank.razorpay.controller.RazorpayPayoutController;
import com.hb.neobank.razorpay.dto.RazorpayReqResTO;
import com.hb.neobank.razorpay.dto.RazorpayTxnReqTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Arrays;

@ControllerAdvice(assignableTypes = RazorpayPayoutController.class)
public class RazorpayPayoutValidator implements Validator {

    @Override
    public boolean supports(Class<?> paramClass) {
        return RazorpayReqResTO.class.equals(paramClass) || RazorpayTxnReqTO.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        RazorpayReqResTO razorpayPayout = (RazorpayReqResTO) obj;
        if (razorpayPayout != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountNumber", "M", "Mandatory");
            if (razorpayPayout.getFundAmount() == null || razorpayPayout.getFundAmount().doubleValue() <= 0) {
                errors.rejectValue("fundAmount", "I", "Invalid");
            } else {
                Integer amount = (int) (razorpayPayout.getFundAmount().doubleValue() * 100);
                razorpayPayout.setAmount(amount);
                razorpayPayout.setFundAmount(null);
            }
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mode", "M", "Mandatory");
            if (razorpayPayout.getMode() != null && !Arrays.asList(RazorpayConst.PAYMENT_MODE).contains(razorpayPayout.getMode())) {
                errors.rejectValue("mode", "I", "Invalid");
            }
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "purpose", "M", "Mandatory");
            if (razorpayPayout.getPurpose() != null && !Arrays.asList(RazorpayConst.PAYMENT_PURPOSE).contains(razorpayPayout.getPurpose())) {
                errors.rejectValue("purpose", "I", "Invalid");
            }
            if (razorpayPayout.getFundAccount() != null) {
                if (razorpayPayout.getFundAccount().getAccountType() != null
                        && !razorpayPayout.getFundAccount().getAccountType().isEmpty()) {
                    if (Arrays.asList(RazorpayConst.ACCOUNT_TYPE).contains(razorpayPayout.getFundAccount().getAccountType())) {
                        switch (razorpayPayout.getFundAccount().getAccountType()) {
                            case RazorpayConst.AccountTypes.BANK_ACCOUNT: {
                                if (razorpayPayout.getFundAccount().getBankAccount() != null) {
                                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fundAccount.bankAccount.ifsc", "M", "Mandatory");
                                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fundAccount.bankAccount.name", "M", "Mandatory");
                                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fundAccount.bankAccount.accountNumber", "M", "Mandatory");
                                } else {
                                    errors.rejectValue("fundAccount.bankAccount", "M", "Mandatory");
                                }
                                break;
                            }
                        }
                    } else {
                        errors.rejectValue("mode", "I", "Invalid");
                    }
                } else {
                    errors.rejectValue("fundAccount.accountType", "M", "Mandatory");
                }
                if (razorpayPayout.getFundAccount().getContact() != null) {
                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fundAccount.contact.name", "M", "Mandatory");
                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fundAccount.contact.contact", "M", "Mandatory");
                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fundAccount.contact.email", "M", "Mandatory");
                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fundAccount.contact.type", "M", "Mandatory");
                    if (razorpayPayout.getFundAccount().getContact().getType() != null
                            && !Arrays.asList(RazorpayConst.CONTACT_TYPE).contains(razorpayPayout.getFundAccount().getContact().getType())) {
                        errors.rejectValue("fundAccount.contact.type", "I", "Invalid");
                    }
                } else {
                    errors.rejectValue("fundAccount.contact", "M", "Mandatory");
                }
            } else {
                errors.rejectValue("fundAccount", "M", "Mandatory");
            }
        }
    }
}
