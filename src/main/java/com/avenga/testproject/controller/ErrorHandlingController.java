package com.avenga.testproject.controller;

import com.avenga.testproject.entity.Error;
import com.avenga.testproject.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandlingController {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public Error handleServiceException(ServiceException ex, HandlerMethod hm) {
        log.error("handleMethodArgumentNotValidException: message: {}, method: {}", ex.getMessage(), hm.getMethod().getName(), ex);
        return new Error(ex.getMessage(), ex.getErrorCode(), LocalDateTime.now());
    }
}
