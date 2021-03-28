package com.orange.mainservice.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName, String searchFieldName, Object searchFieldValue){
        super(String.format("Resource: %s with %s = '%s' not found.", resourceName, searchFieldName, searchFieldValue));
    }

    public ResourceNotFoundException(String resourcesName) {
        super(String.format("Could not find any %s resources.", resourcesName));
    }
}
