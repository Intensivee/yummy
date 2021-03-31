package com.orange.mainservice.exception;

public class ResourceCreateException extends RuntimeException {

    private static final String ID_IS_NOT_NULL_ERROR_MESSAGE = "Id should be null. Can not create new object with specified id: ";

    public ResourceCreateException(Long id) {
        super(String.format("%s %d", ID_IS_NOT_NULL_ERROR_MESSAGE, id));
    }
}
