package com.avenga.testproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Error {
    private String message;
    private int errorCode;
    private LocalDateTime date;
}
