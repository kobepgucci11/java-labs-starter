package edu.course.lab05;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class TaskFileLoader {

    private static final String EXPECTED_HEADER = "id;title;category;estimatedHours;status";
    private static final int EXPECTED_FIELDS = 5;

    public LoadResult load(Path path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DataLoadingException("Cannot read " + path, e);
        }

        if (lines.isEmpty()) {
            throw new DataLoadingException("File is empty: " + path, null);
        }

        String header = lines.get(0);
        if (!EXPECTED_HEADER.equals(header)) {
            throw new DataLoadingException("Invalid header in " + path + ": " + header, null);
        }

        List<Task> validTasks = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) continue;

            int lineNumber = i + 1;

            String[] parts = line.split(";", -1);
            if (parts.length != EXPECTED_FIELDS) {
                errors.add(new ValidationError(lineNumber, "row", "Expected " + EXPECTED_FIELDS + " fields, got " + parts.length));
                continue;
            }

            String id = parts[0].trim();
            String title = parts[1].trim();
            String category = parts[2].trim();
            String hoursStr = parts[3].trim();
            String statusStr = parts[4].trim();

            if (id.isEmpty()) {
                errors.add(new ValidationError(lineNumber, "id", "Field is empty"));
                continue;
            }
            if (title.isEmpty()) {
                errors.add(new ValidationError(lineNumber, "title", "Field is empty"));
                continue;
            }
            if (category.isEmpty()) {
                errors.add(new ValidationError(lineNumber, "category", "Field is empty"));
                continue;
            }
            if (hoursStr.isEmpty()) {
                errors.add(new ValidationError(lineNumber, "estimatedHours", "Field is empty"));
                continue;
            }
            if (statusStr.isEmpty()) {
                errors.add(new ValidationError(lineNumber, "status", "Field is empty"));
                continue;
            }

            int estimatedHours;
            try {
                estimatedHours = Integer.parseInt(hoursStr);
            } catch (NumberFormatException e) {
                errors.add(new ValidationError(lineNumber, "estimatedHours", "Invalid number: " + hoursStr));
                continue;
            }

            Status status;
            try {
                status = Status.valueOf(statusStr);
            } catch (IllegalArgumentException e) {
                errors.add(new ValidationError(lineNumber, "status", "Unknown status: " + statusStr));
                continue;
            }

            validTasks.add(new Task(id, title, category, estimatedHours, status));
        }

        return new LoadResult(validTasks, errors);
    }
}