package com.orange.mainservice.exception;

public class ResourceUniqueConstraintException extends RuntimeException {

    public ResourceUniqueConstraintException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("Resource: %s with %s = '%s' already exists.", resourceName, fieldName, fieldValue));
    }
}
