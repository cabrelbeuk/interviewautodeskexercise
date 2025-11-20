package com.fileuploadex.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fileuploadex.domain.FileRecord;
import com.fileuploadex.exception.FileProcessingException;
import com.fileuploadex.exception.InvalidFileTypeException;
import com.fileuploadex.repository.InMemoryFileRecordRepository;
import com.fileuploadex.validator.FileTypeValidator;
import com.fileuploadex.validator.SimpleFileTypeValidator;

class FileUploadServiceTest {

    private InMemoryFileRecordRepository repository;
    private FileUploadService service;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        repository = new InMemoryFileRecordRepository();
        FileProcessor processor = new TextFileProcessor();
        FileTypeValidator validator = new SimpleFileTypeValidator();
        service = new FileUploadService(repository, processor, validator);
    }

    @Test
    void successfulUploadSavesRecord() {
        String content = "hello\nworld";
        InputStream stream =
            new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

        FileRecord record = service.uploadAndProcess("data.txt", stream);

        assertNotNull(record.getId());
        assertEquals("data.txt", record.getFileName());
        assertEquals(2, record.getProcessingResult().getLineCount());
        assertEquals(2, record.getProcessingResult().getWordCount());
        assertEquals(1, repository.count());
    }

    @Test
    void invalidFileTypeThrowsInvalidFileTypeException() {
        String content = "whatever";
        InputStream stream =
            new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

        InvalidFileTypeException ex = assertThrows(
            InvalidFileTypeException.class,
            () -> service.uploadAndProcess("virus.exe", stream)
        );
        assertEquals("Unsupported file type. Only .txt and .csv files are allowed.", ex.getMessage());
        assertEquals(0, repository.count());
    }

    @Test
    void ioErrorIsWrappedIntoFileProcessingException() {
        // Create a FileProcessor that always throws IOException
        FileProcessor failingProcessor = inputStream -> {
            throw new java.io.IOException("Simulated I/O failure");
        };
        FileTypeValidator validator = new SimpleFileTypeValidator();
        FileUploadService faultyService =
            new FileUploadService(repository, failingProcessor, validator);

        String content = "data";
        InputStream stream =
            new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

        FileProcessingException ex = assertThrows(
            FileProcessingException.class,
            () -> faultyService.uploadAndProcess("file.txt", stream)
        );
        assertTrue(ex.getMessage().startsWith("Failed to process uploaded file"));

        assertEquals(0, repository.count());
    }
}
