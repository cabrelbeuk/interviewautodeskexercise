package com.fileuploadex.domain;

import java.util.Objects;

/**
 * Immutable value object representing the result of processing a file.
 */
public final class FileProcessingResult {

    private final long lineCount;
    private final long wordCount;

    public FileProcessingResult(long lineCount, long wordCount) {
        this.lineCount = lineCount;
        this.wordCount = wordCount;
    }

    public long getLineCount() {
        return lineCount;
    }

    public long getWordCount() {
        return wordCount;
    }

    @Override
    public String toString() {
        return "FileProcessingResult{" +
            "lineCount=" + lineCount +
            ", wordCount=" + wordCount +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileProcessingResult that)) return false;
        return lineCount == that.lineCount && wordCount == that.wordCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineCount, wordCount);
    }
}
