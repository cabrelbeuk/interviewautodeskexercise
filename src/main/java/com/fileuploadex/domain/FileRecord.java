package com.fileuploadex.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * Entity representing a stored file record.
 */
public final class FileRecord {

    private final UUID id;
    private final String fileName;
    private final FileProcessingResult processingResult;
    private final Instant uploadedAt;

    public FileRecord(UUID id,
                      String fileName,
                      FileProcessingResult processingResult,
                      Instant uploadedAt) {
        this.id = id;
        this.fileName = fileName;
        this.processingResult = processingResult;
        this.uploadedAt = uploadedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public FileProcessingResult getProcessingResult() {
        return processingResult;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    @Override
    public String toString() {
        return "FileRecord{" +
            "id=" + id +
            ", fileName='" + fileName + '\'' +
            ", processingResult=" + processingResult +
            ", uploadedAt=" + uploadedAt +
            '}';
    }
}
