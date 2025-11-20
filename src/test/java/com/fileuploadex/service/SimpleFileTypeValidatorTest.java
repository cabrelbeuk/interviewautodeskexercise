package com.fileuploadex.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fileuploadex.validator.SimpleFileTypeValidator;

class SimpleFileTypeValidatorTest {

    private final SimpleFileTypeValidator validator = new SimpleFileTypeValidator();

    @Test
    void allowsTxtCsv() {
        assertTrue(validator.isAllowed("file.txt"));
        assertTrue(validator.isAllowed("report.csv"));
        assertTrue(validator.isAllowed("UPPERCASE.CSV"));
        assertTrue(validator.isAllowed("mixed.Name.TxT"));
    }

    @Test
    void rejectsNullEmptyNoExtension() {
        assertFalse(validator.isAllowed(null));
        assertFalse(validator.isAllowed(""));
        assertFalse(validator.isAllowed("   "));
        assertFalse(validator.isAllowed("noextension"));
        assertFalse(validator.isAllowed("weird."));
    }

    @Test
    void rejectsOtherExtensions() {
        assertFalse(validator.isAllowed("file.pdf"));
        assertFalse(validator.isAllowed("image.png"));
        assertFalse(validator.isAllowed("script.exe"));
    }
}
