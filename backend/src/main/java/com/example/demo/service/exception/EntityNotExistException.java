package com.example.demo.service.exception;

import lombok.Getter;

@Getter
public class EntityNotExistException extends RuntimeException{
    private final String reason;
    private final String className;

    public EntityNotExistException(String reason, String className) {
        super();
        this.reason = reason;
        this.className = className;
    }
}
