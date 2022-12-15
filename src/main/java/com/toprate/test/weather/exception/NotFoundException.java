package com.toprate.test.weather.exception;

public class NotFoundException extends SystemException{
    public NotFoundException() {
        super("Not Found");
    }
}
