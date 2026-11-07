package com.taskplanner.service.sort;

import com.taskplanner.model.Task;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@Primary
public class PrioritySortStrategy implements TaskSortStrategy {
    @Override
    public String key() {
        return "priority";
    }

    @Override
    public String displayName() {
        return "За пріоритетом";
    }

    @Override
    public Comparator<Task> comparator() {
        return Comparator
                .comparingInt((Task t) -> t.getPriority() == null ? 0 : t.getPriority().getWeight())
                .thenComparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()));
    }
}
