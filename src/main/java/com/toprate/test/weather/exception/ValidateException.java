package com.toprate.test.weather.exception;

import org.springframework.validation.BeanPropertyBindingResult;

public class ValidateException extends SystemException{

    private final BeanPropertyBindingResult bindingResult;

    public ValidateException(BeanPropertyBindingResult result) {
        super("a ValidateException occurred.");
        this.bindingResult = result;
    }

    public BeanPropertyBindingResult getBindingResult() {
        return this.bindingResult;
    }
}
