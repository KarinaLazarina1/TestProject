package com.avenga.testproject.exception;

import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode
public abstract class ServiceException extends RuntimeException{

    public ServiceException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
