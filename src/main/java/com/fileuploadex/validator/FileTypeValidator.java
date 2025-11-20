package com.fileuploadex.validator;

/**
 * Validates whether a file name is of an allowed type.
 */
public interface FileTypeValidator {

    boolean isAllowed(String fileName);
}
