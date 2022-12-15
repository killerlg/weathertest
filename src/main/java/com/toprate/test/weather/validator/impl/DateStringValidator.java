package com.toprate.test.weather.validator.impl;

import com.toprate.test.weather.validator.DateString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateStringValidator implements ConstraintValidator<DateString, String> {

    private DateTimeFormatter fomatter;

    @Override
    public void initialize(DateString dateFormat) {
        // Get the format of a string date.
        this.fomatter = DateTimeFormatter.ofPattern(dateFormat.format());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        try {
            // The input item is blank.
            if (!StringUtils.hasLength(value)) {
                return true;
            }
            return value.equals(LocalDate.parse(value, fomatter).format(fomatter));
        } catch (DateTimeParseException e) {
            return false;
        }

    }
}

