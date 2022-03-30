package com.orange.mainservice.exception;

public class ImageConversionException extends RuntimeException {

    public ImageConversionException() {
        super("Image was not recognised.");
    }
}
