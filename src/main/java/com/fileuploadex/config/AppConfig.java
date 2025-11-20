package com.fileuploadex.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Loads application config from application.properties.
 * Keeps defaults if the file or keys are missing.
 */
public final class AppConfig {

    private static final String PROPERTIES_FILE = "application.properties";
    private static final String ALLOWED_EXTENSIONS_KEY = "allowed.extensions";
    private static final Set<String> DEFAULT_ALLOWED_EXTENSIONS = Set.of("txt", "csv");

    private final Properties properties;

    private AppConfig(Properties properties) {
        this.properties = properties;
    }

    public static AppConfig load() {
        Properties props = new Properties();

        try (InputStream is = AppConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException ignored) {
            // Keep defaults if config cannot be read
        }

        return new AppConfig(props);
    }

    public Set<String> getAllowedExtensions() {
        String raw = properties.getProperty(ALLOWED_EXTENSIONS_KEY);

        if (raw == null || raw.isBlank()) {
            // Return defaults if missing or blank
            return DEFAULT_ALLOWED_EXTENSIONS;
        }

        Set<String> parsed = Stream.of(raw.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(String::toLowerCase)
            .collect(Collectors.toSet());

        return parsed.isEmpty() ? DEFAULT_ALLOWED_EXTENSIONS : parsed;
    }
}
