package com.example.demo.persistence.exception;

public class EntityCreationFaileException extends RuntimeException {
    private final String ClassName;
    private final long entityId;

    public EntityCreationFaileException(String className, long entityId) {
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
