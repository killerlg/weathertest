package com.toprate.test.weather.service.impl;

import com.toprate.test.weather.exception.ValidateException;
import com.toprate.test.weather.service.ValidateService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

@Service
public class ValidateServiceImpl extends CustomValidatorBean implements ValidateService {
    @Override
    public void validate(Object target) {
        // BeanPropertyBindingResult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(target,
                StringUtils.uncapitalize(target.getClass().getSimpleName()));
        // run validate
        super.validate(target, result);
        // check result
        if (result.hasErrors()) {
            throw new ValidateException(result);
        }
    }
}
