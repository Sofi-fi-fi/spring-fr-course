package com.taskplanner.service.sort;

import com.taskplanner.model.Task;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class DueDateSortStrategy implements TaskSortStrategy {
    @Override
    public String key() {
        return "dueDate";
    }

    @Override
    public String displayName() {
        return "За датою";
    }

    @Override
    public Comparator<Task> comparator() {
        return Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()));
    }
}
