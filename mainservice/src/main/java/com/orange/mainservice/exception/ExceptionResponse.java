package com.orange.mainservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public final class ExceptionResponse {

    private final HttpStatus status;
    private final Date timestamp;
    private final String message;

    public ExceptionResponse(HttpStatus status, Date timestamp, List<String> messages) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = messages.toString();
    }
}
