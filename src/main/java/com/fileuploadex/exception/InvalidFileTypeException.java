package com.fileuploadex.exception;

/**
 * Thrown when an uploaded file has an unsupported file type.
 */
public final class InvalidFileTypeException extends RuntimeException {

    public InvalidFileTypeException(String message) {
        super(message);
    }
}
