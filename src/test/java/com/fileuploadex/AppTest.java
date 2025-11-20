package com.fileuploadex;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

/**
 * Simple sanity test to ensure the main method runs without throwing.
 */
class AppTest {

    @Test
    void mainRunsWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[0]));
    }
}
