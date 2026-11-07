package com.taskplanner.service;

import com.taskplanner.model.TaskStatus;

import java.time.LocalDateTime;

public record TaskCommand(String title,
                          String description,
                          LocalDateTime dueDate,
                          String priorityCode,
                          TaskStatus status) {
}
