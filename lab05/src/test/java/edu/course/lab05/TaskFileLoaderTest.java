package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFileLoaderTest {

    @TempDir
    Path tempDir;

    private Path writeFile(String content) throws IOException {
        Path file = tempDir.resolve("tasks.csv");
        Files.write(file, List.of(content.split("\n")), StandardCharsets.UTF_8);
        return file;
    }

    @Test
    void testCorrectFile() throws IOException {
        Path file = writeFile(
                "id;title;category;estimatedHours;status\n" +
                        "T-101;Prepare release;backend;8;READY\n" +
                        "T-102;Write docs;frontend;4;DONE"
        );
        TaskFileLoader loader = new TaskFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(2, result.getValidTasks().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("T-101", result.getValidTasks().get(0).getId());
        assertEquals(Status.READY, result.getValidTasks().get(0).getStatus());
    }

    @Test
    void testUnicodeText() throws IOException {
        Path file = writeFile(
                "id;title;category;estimatedHours;status\n" +
                        "T-101;Подготовить релиз;backend;8;READY"
        );
        TaskFileLoader loader = new TaskFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(1, result.getValidTasks().size());
        assertEquals("Подготовить релиз", result.getValidTasks().get(0).getTitle());
    }

    @Test
    void testEmptyField() throws IOException {
        Path file = writeFile(
                "id;title;category;estimatedHours;status\n" +
                        "T-101;;backend;8;READY"
        );
        TaskFileLoader loader = new TaskFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(0, result.getValidTasks().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("title", result.getErrors().get(0).getField());
    }

    @Test
    void testInvalidNumber() throws IOException {
        Path file = writeFile(
                "id;title;category;estimatedHours;status\n" +
                        "T-101;Prepare release;backend;eight;READY"
        );
        TaskFileLoader loader = new TaskFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(0, result.getValidTasks().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("estimatedHours", result.getErrors().get(0).getField());
    }

    @Test
    void testUnknownStatus() throws IOException {
        Path file = writeFile(
                "id;title;category;estimatedHours;status\n" +
                        "T-101;Prepare release;backend;8;UNKNOWN"
        );
        TaskFileLoader loader = new TaskFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(0, result.getValidTasks().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void testWrongColumnCount() throws IOException {
        Path file = writeFile(
                "id;title;category;estimatedHours;status\n" +
                        "T-101;Prepare release;backend;8"
        );
        TaskFileLoader loader = new TaskFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(0, result.getValidTasks().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("row", result.getErrors().get(0).getField());
    }

    @Test
    void testValidAndInvalidLinesTogether() throws IOException {
        Path file = writeFile(
                "id;title;category;estimatedHours;status\n" +
                        "T-101;Prepare release;backend;8;READY\n" +
                        "T-102;;frontend;4;DONE\n" +
                        "T-103;Write tests;qa;bad;READY\n" +
                        "T-104;Deploy;devops;2;DONE"
        );
        TaskFileLoader loader = new TaskFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(2, result.getValidTasks().size());
        assertEquals(2, result.getErrors().size());
    }

    @Test
    void testMissingFile() {
        Path missing = tempDir.resolve("missing.csv");
        TaskFileLoader loader = new TaskFileLoader();

        DataLoadingException ex = assertThrows(DataLoadingException.class, () -> loader.load(missing));
        assertNotNull(ex.getCause());
        assertTrue(ex.getCause() instanceof IOException);
    }
}