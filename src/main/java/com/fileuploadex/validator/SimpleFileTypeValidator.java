package com.fileuploadex.validator;

import java.util.Objects;
import java.util.Set;

/**
 * Validates that extensions of file are allowed as per config.
 */
public final class SimpleFileTypeValidator implements FileTypeValidator {

    private final Set<String> allowedExtensions;

    public SimpleFileTypeValidator(Set<String> allowedExtensions) {
        Objects.requireNonNull(allowedExtensions, "allowedExtensions must not be null");
        this.allowedExtensions = Set.copyOf(allowedExtensions);
    }

    @Override
    public boolean isAllowed(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return false;
        }

        int dot = fileName.lastIndexOf('.');
        if (dot < 0 || dot == fileName.length() - 1) {
            return false; // no extension or trailing dot
        }

        String ext = fileName.substring(dot + 1).trim().toLowerCase();
        return allowedExtensions.contains(ext);
    }
}
