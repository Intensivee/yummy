package com.orange.mainservice.exception;

public class EmptyPdfTableDataException extends RuntimeException {

    public EmptyPdfTableDataException() {
        super("Data rows used to create pdf table can not be empty.");
    }
}
