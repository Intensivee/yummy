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
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFound(ResourceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createBody(e, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({ResourceCreateException.class, PathNotMatchBodyException.class})
    public ResponseEntity<ExceptionResponse> handleResourceCreate(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createBody(e, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(RegistrationException.class)
    public final ResponseEntity<ExceptionResponse> conflictHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(this.createBody(e, HttpStatus.CONFLICT));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               WebRequest request) {
        List<String> messages = getMethodArgumentNotValidMessages(ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createBodyWithMultipleMessages(ex.getClass().getTypeName(), HttpStatus.BAD_REQUEST, messages));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationFailed(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createBody(e, HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(ResourceUniqueConstraintException.class)
    public ResponseEntity<ExceptionResponse> handleResourceUniqueConstraint(ResourceUniqueConstraintException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(createBody(e, HttpStatus.CONFLICT));
    }

    @ExceptionHandler(FileDownloadException.class)
    public ResponseEntity<ExceptionResponse> handleFileDownloadException(FileDownloadException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createBody(e, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(ImageConversionException.class)
    public ResponseEntity<ExceptionResponse> handleImageConversionException(ImageConversionException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createBody(e, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(EmptyPdfTableDataException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyPFFTableDataException(EmptyPdfTableDataException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createBody(e, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private List<String> getMethodArgumentNotValidMessages(MethodArgumentNotValidException e) {
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
                Objects.nonNull(e.getLocalizedMessage()) ? e.getLocalizedMessage() : e.toString()
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
