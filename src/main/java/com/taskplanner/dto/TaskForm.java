package com.taskplanner.dto;

import com.taskplanner.model.Task;
import com.taskplanner.model.TaskStatus;
import com.taskplanner.service.TaskCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class TaskForm {
    @NotBlank(message = "Task title must not be blank")
    @Size(max = 200, message = "Task title must not exceed 200 characters")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dueDate;

    @NotBlank(message = "Please select a priority")
    private String priorityCode;

    @NotNull(message = "Please select a status")
    private TaskStatus status = TaskStatus.TODO;

    public static TaskForm from(Task task) {
        TaskForm form = new TaskForm();
        form.setTitle(task.getTitle());
        form.setDescription(task.getDescription());
        form.setDueDate(task.getDueDate());
        form.setPriorityCode(task.getPriority().getCode());
        form.setStatus(task.getStatus());
        return form;
    }

    public TaskCommand toCommand() {
        return new TaskCommand(title, description, dueDate, priorityCode, status);
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

    public String getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(String priorityCode) {
        this.priorityCode = priorityCode;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
