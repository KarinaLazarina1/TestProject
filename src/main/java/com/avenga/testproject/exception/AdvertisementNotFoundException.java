package com.avenga.testproject.exception;


import org.springframework.http.HttpStatus;

public class AdvertisementNotFoundException extends ServiceException {
    private static final String DEFAULT_MESSAGE = "Advertisement not found";

    public AdvertisementNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public int getErrorCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
