package com.fileuploadex.validator;

import java.util.Locale;

/**
 * Simple validator that allows only .txt and .csv files (case-insensitive).
 */
public final class SimpleFileTypeValidator implements FileTypeValidator {

    @Override
    public boolean isAllowed(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex <= 0 || dotIndex == fileName.length() - 1) {
            // No extension or dot at the end.
            return false;
        }

        String extension = fileName.substring(dotIndex + 1)
                                   .toLowerCase(Locale.ROOT);

        return "txt".equals(extension) || "csv".equals(extension);
    }
}
