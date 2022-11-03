package com.example.demo.presentation.exception.entity;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ExceptionWrapper {

    private final String cause;
    private final int errorCode;
    private final ZonedDateTime timeStamp;

    public ExceptionWrapper(String cause, int errorCode, ZonedDateTime timeStamp) {
        this.cause = cause;
        this.errorCode = errorCode;
        this.timeStamp = timeStamp;
    }
}
