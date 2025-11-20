package com.fileuploadex.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.fileuploadex.domain.FileRecord;

/**
 * In-memory implementation of FileRecordRepository.
 */
public final class InMemoryFileRecordRepository implements FileRecordRepository {

    private final Map<UUID, FileRecord> storage = new ConcurrentHashMap<>();

    @Override
    public void save(FileRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("FileRecord must not be null.");
        }
        storage.put(record.getId(), record);
    }

    @Override
    public Optional<FileRecord> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public long count() {
        return storage.size();
    }
}
