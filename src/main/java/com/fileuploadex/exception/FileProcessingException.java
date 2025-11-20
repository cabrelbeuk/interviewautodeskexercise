package com.fileuploadex.exception;

/**
 * Wraps low-level processing errors with a generic message.
 */
public final class FileProcessingException extends RuntimeException {

    public FileProcessingException(String message, Throwable exception) {
        super(message, exception);
    }
}
