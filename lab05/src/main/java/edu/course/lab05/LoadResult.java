package edu.course.lab05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LoadResult {
    private final List<Task> validTasks;
    private final List<ValidationError> errors;

    public LoadResult(List<Task> validTasks, List<ValidationError> errors) {
        this.validTasks = Collections.unmodifiableList(new ArrayList<>(validTasks));
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
    }

    public List<Task> getValidTasks() { return validTasks; }
    public List<ValidationError> getErrors() { return errors; }

    public boolean hasErrors() { return !errors.isEmpty(); }
}