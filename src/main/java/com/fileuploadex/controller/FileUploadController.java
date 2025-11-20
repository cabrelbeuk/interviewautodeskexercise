package com.fileuploadex.controller;

import java.io.InputStream;
import java.util.logging.Logger;

import com.fileuploadex.domain.FileRecord;
import com.fileuploadex.service.FileUploadService;

/**
    * "Controller" layer handling file upload requests.
 */
public final class FileUploadController {

    private static final Logger LOGGER =
        Logger.getLogger(FileUploadController.class.getName());

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    //entry point for uploading a file.
    public FileUploadResponse upload(String fileName, InputStream contentStream) {
        LOGGER.info(() -> "Controller: handling upload for file " + fileName);

        FileRecord record = fileUploadService.uploadAndProcess(fileName, contentStream);

        return new FileUploadResponse(
            record.getId(),
            record.getFileName(),
            record.getProcessingResult().getLineCount(),
            record.getProcessingResult().getWordCount(),
            record.getUploadedAt()
        );
    }
}
