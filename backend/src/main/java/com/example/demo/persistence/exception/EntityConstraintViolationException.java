package com.example.demo.persistence.exception;

public class EntityConstraintViolationException extends RuntimeException {
    private final String ClassName;
    private final long entityId;

    public EntityConstraintViolationException(String className, long entityId) {
        super();
        ClassName = className;
        this.entityId = entityId;
    }

    public String getClassName() {
        return ClassName;
    }

    public long getEntityId() {
        return entityId;
    }
}
