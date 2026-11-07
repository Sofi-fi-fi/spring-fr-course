package com.taskplanner.validation;

import com.taskplanner.model.Task;
import com.taskplanner.exception.TaskValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskValidator {
    private static final int DESCRIPTION_MAX_LENGTH = 2000;

    private final int titleMaxLength;

    public TaskValidator(@Value("${app.tasks.title-max-length:200}") int titleMaxLength) {
        this.titleMaxLength = titleMaxLength;
    }

    public void validate(Task task) {
        List<String> errors = new ArrayList<>();
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            errors.add("Назва задачі не може бути порожньою");
        } else if (task.getTitle().length() > titleMaxLength) {
            errors.add("Назва задачі не може бути довшою за " + titleMaxLength + " символів");
        }
        if (task.getDescription() != null && task.getDescription().length() > DESCRIPTION_MAX_LENGTH) {
            errors.add("Опис не може бути довшим за " + DESCRIPTION_MAX_LENGTH + " символів");
        }
        if (task.getPriority() == null) {
            errors.add("Пріоритет обов'язковий");
        }
        if (!errors.isEmpty()) {
            throw new TaskValidationException(errors);
        }
    }

    public int getTitleMaxLength() {
        return titleMaxLength;
    }
}
