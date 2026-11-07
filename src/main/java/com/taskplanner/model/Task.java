package com.taskplanner.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private Long id;
    private String title;
    private String description;

    private LocalDateTime dueDate;
    private Priority priority;
    private TaskStatus status = TaskStatus.TODO;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public Task() {
    }

    public Task(String title, String description, LocalDateTime dueDate, Priority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public boolean isCompleted() {
        return status == TaskStatus.DONE;
    }

    public Task copy() {
        Task copy = new Task(title, description, dueDate, priority);
        copy.id = id;
        copy.status = status;
        copy.createdAt = createdAt;
        copy.completedAt = completedAt;
        return copy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", title='" + title + "', status=" + status + '}';
    }
}
