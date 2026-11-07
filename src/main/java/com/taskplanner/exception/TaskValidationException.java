package com.taskplanner.exception;

import java.util.List;

public class TaskValidationException extends RuntimeException {
    private final List<String> errors;

    public TaskValidationException(List<String> errors) {
        super(String.join("; ", errors));
        this.errors = List.copyOf(errors);
    }

    public List<String> getErrors() {
        return errors;
    }
}
