package com.toprate.test.weather.api.controller;

import com.toprate.test.weather.exception.ValidateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiErrorControllerAdvice {

    @ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class,
            ValidateException.class })
    public ResponseEntity<Object> handleValidatedException(Exception ex, WebRequest request) {

        List<ObjectError> objectErrors = new ArrayList<>();
        if (ex instanceof BindException) {
            objectErrors = ((BindException) ex).getAllErrors();
        } else if (ex instanceof MethodArgumentNotValidException) {
            objectErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
        } else if (ex instanceof ValidateException) {
            objectErrors = ((ValidateException) ex).getBindingResult().getAllErrors();
        }

        return new ResponseEntity<>(objectErrors, HttpStatus.BAD_REQUEST) ;
    }
}
