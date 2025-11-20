package com.fileuploadex.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fileuploadex.domain.FileProcessingResult;
import com.fileuploadex.domain.FileRecord;
import com.fileuploadex.exception.FileProcessingException;
import com.fileuploadex.exception.InvalidFileTypeException;
import com.fileuploadex.repository.FileRecordRepository;
import com.fileuploadex.validator.FileTypeValidator;

/**
 * Core business service orchestrating validation, processing and persistence.
 */
public final class FileUploadService {

    private static final Logger LOGGER =
        Logger.getLogger(FileUploadService.class.getName());

    private final FileRecordRepository repository;
    private final FileProcessor fileProcessor;
    private final FileTypeValidator fileTypeValidator;

    public FileUploadService(FileRecordRepository repository,
                             FileProcessor fileProcessor,
                             FileTypeValidator fileTypeValidator) {
        this.repository = repository;
        this.fileProcessor = fileProcessor;
        this.fileTypeValidator = fileTypeValidator;
    }

    /**
     * Do the following actions:
     *  - validate file type
     *  - process file contents
     *  - save record
     *  - log key actions and errors
     */
    public FileRecord uploadAndProcess(String fileName, InputStream contentStream) {
        String safeName = safeFileName(fileName);
        LOGGER.info(() -> "Received file upload request: " + safeName);

        try {
            validateFileType(fileName);

            LOGGER.info(() -> "Starting processing for file: " + safeName);
            FileProcessingResult result = fileProcessor.process(contentStream);
            LOGGER.info(() -> String.format(
                "Completed processing for file: %s (lines=%d, words=%d)",
                safeName, result.getLineCount(), result.getWordCount()
            ));

            FileRecord record = saveRecord(fileName, result);
            LOGGER.info(() -> "Saved FileRecord with id: " + record.getId());

            return record;
        } catch (InvalidFileTypeException ex) {
            LOGGER.log(Level.WARNING, "File type validation failed: " + safeName, ex);
            throw ex;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "I/O error while processing file: " + safeName, ex);
            throw new FileProcessingException(
                "Failed to process uploaded file due to internal I/O error.", ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Unexpected error while handling file: " + safeName, ex);
            throw new FileProcessingException(
                "An unexpected error occurred while handling the uploaded file.", ex);
        }
    }

    private void validateFileType(String fileName) {
        if (!fileTypeValidator.isAllowed(fileName)) {
            throw new InvalidFileTypeException(
                "Unsupported file type. Only .txt and .csv files are allowed.");
        }
    }

    private FileRecord saveRecord(String fileName, FileProcessingResult result) {
        FileRecord record = new FileRecord(
            UUID.randomUUID(),
            fileName,
            result,
            Instant.now()
        );
        repository.save(record);
        return record;
    }

    private String safeFileName(String fileName) {
        return fileName == null ? "<null>" : fileName;
    }
}
