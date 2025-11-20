package com.fileuploadex.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.fileuploadex.domain.FileProcessingResult;

class TextFileProcessorTest {

    private final TextFileProcessor processor = new TextFileProcessor();

    @Test
    void countsLinesAndWords() throws Exception {
        String content = """
                         Hello world
                         This is a   test
                         
                          Last line here """;
        InputStream stream =
            new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

        FileProcessingResult result = processor.process(stream);

        assertEquals(4, result.getLineCount());
        assertEquals(9, result.getWordCount());

    }

    @Test
    void handlesWhitespaceOnlyLines() throws Exception {
        String content = "   \nword1   word2\n   \t   ";
        InputStream stream =
            new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

        FileProcessingResult result = processor.process(stream);

        // lines: 3
        // words: "word1", "word2" = 2
        assertEquals(3, result.getLineCount());
        assertEquals(2, result.getWordCount());
    }

    @Test
    void handlesEmptyFile() throws Exception {
        String content = "";
        InputStream stream =
            new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

        FileProcessingResult result = processor.process(stream);

        assertEquals(0, result.getLineCount());
        assertEquals(0, result.getWordCount());
    }

    @Test
    void countsWordSpecialCharacters() throws Exception {
        String content =
            """
            Hello, world!
            Caf\u00e9 na\u00efve r\u00e9sum\u00e9
            foo,bar baz;qux
            symbols #hashtag $money
            """;

        InputStream stream =
            new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

        FileProcessingResult result = processor.process(stream);

        assertEquals(4, result.getLineCount());
        // total words = 2 + 3 + 2 + 3 = 10
        assertEquals(10, result.getWordCount());
    }


    @Test
    void throwsOnNullInputStream() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> processor.process(null)
        );
        assertEquals("InputStream must not be null.", ex.getMessage());
    }
}
