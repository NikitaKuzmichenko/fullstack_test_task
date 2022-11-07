package com.example.demo.presentation.exception.wrapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
public class ExceptionWrapper {

    private final String cause;
    private final int errorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private final ZonedDateTime timeStamp;

    public ExceptionWrapper(String cause, int errorCode, ZonedDateTime timeStamp) {
        this.cause = cause;
        this.errorCode = errorCode;
        this.timeStamp = timeStamp;
    }
}
