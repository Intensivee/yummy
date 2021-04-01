package com.orange.mainservice.exception;

public class PathNotMatchBodyException  extends RuntimeException{

    public PathNotMatchBodyException(Long pathId, Long bodyId) {
        super(String.format("path variable, id: %d does not match request body id: %d", pathId, bodyId));
    }
}
