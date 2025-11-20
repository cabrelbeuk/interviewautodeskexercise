package com.fileuploadex.repository;

import java.util.Optional;
import java.util.UUID;

import com.fileuploadex.domain.FileRecord;

/**
 * Repository abstraction for FileRecord persistence.
 */
public interface FileRecordRepository {

    void save(FileRecord record);

    Optional<FileRecord> findById(UUID id);

    long count();
}
