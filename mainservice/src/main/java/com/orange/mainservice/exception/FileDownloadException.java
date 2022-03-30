package com.orange.mainservice.exception;

public class FileDownloadException extends RuntimeException {

    public FileDownloadException(String url) {
        super("Error when downloading file from url: " + url);
    }
}
