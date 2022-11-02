package com.example.demo.service.exception;

import lombok.Getter;

@Getter
public class EntityNotExistException extends RuntimeException{
    private final long entityId;
    private final String className;

    public EntityNotExistException(long entityId, String className) {
        super();
        this.entityId = entityId;
        this.className = className;
    }
}
