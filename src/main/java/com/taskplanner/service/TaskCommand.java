package com.taskplanner.service;

import java.time.LocalDateTime;

public record TaskCommand(String title, String description, LocalDateTime dueDate, String priorityCode) {
}
