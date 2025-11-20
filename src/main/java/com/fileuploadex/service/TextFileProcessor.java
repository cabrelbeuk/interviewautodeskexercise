package com.fileuploadex.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.fileuploadex.domain.FileProcessingResult;

/**
 * Concrete FileProcessor for text-based files (.txt, .csv).
 */
public final class TextFileProcessor implements FileProcessor {

    @Override
    public FileProcessingResult process(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream must not be null.");
        }

        long lineCount = 0;
        long wordCount = 0;

        try (BufferedReader reader =
                 new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;

                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    // Words are separated by one or more whitespace characters.
                    String[] words = trimmed.split("\\s+");
                    wordCount += words.length;
                }
            }
        }

        return new FileProcessingResult(lineCount, wordCount);
    }
}
