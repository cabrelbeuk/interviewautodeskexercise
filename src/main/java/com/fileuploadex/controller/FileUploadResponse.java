package com.fileuploadex.controller;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO returned to callers after a successful upload.
 */
public final class FileUploadResponse {

    private final UUID id;
    private final String fileName;
    private final long lineCount;
    private final long wordCount;
    private final Instant uploadedAt;

    public FileUploadResponse(UUID id,
                              String fileName,
                              long lineCount,
                              long wordCount,
                              Instant uploadedAt) {
        this.id = id;
        this.fileName = fileName;
        this.lineCount = lineCount;
        this.wordCount = wordCount;
        this.uploadedAt = uploadedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public long getLineCount() {
        return lineCount;
    }

    public long getWordCount() {
        return wordCount;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    @Override
    public String toString() {
        return "FileUploadResponse{" +
            "id=" + id +
            ", fileName='" + fileName + '\'' +
            ", lineCount=" + lineCount +
            ", wordCount=" + wordCount +
            ", uploadedAt=" + uploadedAt +
            '}';
    }
}
