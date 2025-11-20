package com.fileuploadex;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fileuploadex.controller.FileUploadController;
import com.fileuploadex.controller.FileUploadResponse;
import com.fileuploadex.repository.InMemoryFileRecordRepository;
import com.fileuploadex.service.FileProcessor;
import com.fileuploadex.service.FileUploadService;
import com.fileuploadex.service.TextFileProcessor;
import com.fileuploadex.validator.FileTypeValidator;
import com.fileuploadex.validator.SimpleFileTypeValidator;

/**
 * Simple entry point for manual execution and demonstration.
 *
 * Usage:
 *  - No arguments: run built-in demo with in-memory content.
 *  - With arguments: each argument is treated as a file path to process.
 */
public final class App {

    private App() {
        // Utility class
    }

    public static void main(String[] args) {
        // === Manual wiring (in a real app, Dependency Injection would do this) ===
        var repository = new InMemoryFileRecordRepository();
        FileProcessor processor = new TextFileProcessor();
        FileTypeValidator validator = new SimpleFileTypeValidator();
        var service = new FileUploadService(repository, processor, validator);
        var controller = new FileUploadController(service);

        if (args == null || args.length == 0) {
            runDemo(controller, repository);
        } else {
            processFilesFromArgs(args, controller, repository);
        }
    }

    /**
     * Demo mode: uses a hard-coded text content and an invalid file upload to demonstrate behaviour.
     */
    private static void runDemo(FileUploadController controller,
                                InMemoryFileRecordRepository repository) {
        String content = """
                         Hello world
                         This is a test file.
                         
                         Last line here.""";
        try (InputStream stream =
                 new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {

            FileUploadResponse response = controller.upload("example.txt", stream);
            System.out.println("=== Valid upload (demo) ===");
            System.out.println(response);
        } catch (Exception e) {
            System.err.println("Error during demo upload: " + e.getMessage());
        }

        // Invalid upload demo
        String badContent = "Some content";
        try (InputStream stream =
                 new ByteArrayInputStream(badContent.getBytes(StandardCharsets.UTF_8))) {

            controller.upload("malware.exe", stream);
        } catch (Exception e) {
            System.out.println("\n=== Invalid upload (demo) ===");
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\nRecords stored in memory (demo): " + repository.count());
    }

    /**
     * File mode: treats each argument as a path to a file on disk and processes it.
     */
    private static void processFilesFromArgs(String[] args,
                                             FileUploadController controller,
                                             InMemoryFileRecordRepository repository) {
        System.out.println("=== Processing files from arguments ===");

        for (String arg : args) {
            Path path = Path.of(arg);

            if (!Files.exists(path) || !Files.isRegularFile(path)) {
                System.err.println("Skipping: not a regular file or does not exist -> " + path);
                continue;
            }

            String fileName = path.getFileName().toString();

            try (InputStream is = Files.newInputStream(path)) {
                FileUploadResponse response = controller.upload(fileName, is);
                System.out.println("\nProcessed file: " + path);
                System.out.println("Result: " + response);
            } catch (Exception e) {
                System.err.println("Error processing " + path + ": " + e.getMessage());
            }
        }

        System.out.println("\nRecords stored in memory: " + repository.count());
    }
}
