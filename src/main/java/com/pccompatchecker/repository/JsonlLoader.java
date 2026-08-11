package com.pccompatchecker.repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonlLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Reads a jsonl resource file (one JSON object per line) from the classpath
     * and deserializes each line into an instance of the given class.
     */
    public static <T> List<T> load(String resourcePath, Class<T> targetClass) {
        List<T> results = new ArrayList<>();

        InputStream inputStream = JsonlLoader.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new RuntimeException("Resource not found: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    T obj = mapper.readValue(line, targetClass);
                    results.add(obj);
                } catch (IOException e) {
                    // Skip malformed rows instead of crashing the whole load —
                    // log which line failed so it's easy to debug later.
                    System.err.println("Skipped bad line " + lineNumber + " in "
                            + resourcePath + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read " + resourcePath, e);
        }

        return results;
    }
}