package com.taskplanner.model;

public enum TaskStatus {
    TODO("До виконання"),
    IN_PROGRESS("В роботі"),
    DONE("Виконано");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
