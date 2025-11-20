package com.fileuploadex.service;

import java.io.IOException;
import java.io.InputStream;

import com.fileuploadex.domain.FileProcessingResult;

/**
 * Strategy interface for processing file content.
 */
public interface FileProcessor {

    FileProcessingResult process(InputStream inputStream) throws IOException;
}
