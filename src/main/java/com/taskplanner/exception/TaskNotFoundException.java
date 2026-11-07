package com.taskplanner.exception;

public class TaskNotFoundException extends RuntimeException {
    private final Long taskId;

    public TaskNotFoundException(Long taskId) {
        super("Задачу з id=" + taskId + " не знайдено");
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return taskId;
    }
}
