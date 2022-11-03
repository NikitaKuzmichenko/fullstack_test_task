package com.example.demo.persistence.exception;

import lombok.Getter;

@Getter
public class EntityCreationFailException extends RuntimeException {
    private final String ClassName;

    public EntityCreationFailException(String className, long entityId) {
        super();
        ClassName = className;
    }
}
