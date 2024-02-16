package com.hb.neobank.common;

import org.springframework.validation.Errors;

public class ValidationErrorBuilder {

    public static ValidationError fromBindingErrors(Errors errors) {
        ValidationError error = new ValidationError();
        Integer count = 0;
        error.setStatus(400);
        error.setCode("ERROR");
        error.setMessage("Field Error Found");
        for (org.springframework.validation.FieldError feildError : errors.getFieldErrors()) {
            count++;
            FieldError fieldError = new FieldError();
            fieldError.setFieldName(feildError.getField());
            fieldError.setMessage(feildError.getDefaultMessage());
            error.getFieldErrors().add(fieldError);
        }
        error.setTotalErrorCount(count);
        return error;
    }
}
