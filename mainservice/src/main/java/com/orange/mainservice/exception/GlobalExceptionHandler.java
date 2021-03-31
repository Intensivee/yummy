package com.orange.mainservice.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFound(ResourceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createBody(e, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ResourceCreateException.class)
    public ResponseEntity<ExceptionResponse> handleResourceCreate(ResourceCreateException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createBody(e, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  WebRequest request) {
        List<String> messages = getMethodArgumentNotValidMessages(ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createBodyWithMultipleMessages(ex.getClass().getTypeName(), HttpStatus.BAD_REQUEST, messages));
    }

    private List<String> getMethodArgumentNotValidMessages(MethodArgumentNotValidException e){
        return e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
    }

    private ExceptionResponse createBody(Exception e, HttpStatus httpStatus) {
        return new ExceptionResponse(
                httpStatus,
                new Date(),
                e.getClass().getTypeName(),
                e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.toString()
        );
    }

    private ExceptionResponse createBodyWithMultipleMessages(String exceptionName,
                                                             HttpStatus httpStatus,
                                                             List<String> messages){
        return new ExceptionResponse(
                httpStatus,
                new Date(),
                exceptionName,
                messages
        );
    }
}
