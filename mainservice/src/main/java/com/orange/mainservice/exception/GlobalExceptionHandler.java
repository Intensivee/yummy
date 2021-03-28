package com.orange.mainservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<ExceptionResponse> handleCourseException(ResourceNotFoundException e){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(httpStatus).body(createBody(e, httpStatus));
    }

    private ExceptionResponse createBody(Exception e, HttpStatus httpStatus) {
        return new ExceptionResponse(
                httpStatus,
                new Date(),
                e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.toString()
        );
    }
}
