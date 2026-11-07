package com.taskplanner.service.sort;

import com.taskplanner.model.Task;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskSorter {
    private final Map<String, TaskSortStrategy> strategies = new LinkedHashMap<>();
    private final TaskSortStrategy defaultStrategy;

    public TaskSorter(List<TaskSortStrategy> allStrategies, TaskSortStrategy defaultStrategy) {
        allStrategies.forEach(s -> strategies.put(s.key(), s));
        this.defaultStrategy = defaultStrategy;
    }

    public TaskSortStrategy resolve(String key) {
        return key == null ? defaultStrategy : strategies.getOrDefault(key, defaultStrategy);
    }

    public List<Task> sort(List<Task> tasks, String key, SortDirection direction) {
        Comparator<Task> comparator = resolve(key).comparator();
        if (direction == SortDirection.DESC) {
            comparator = comparator.reversed();
        }
        return tasks.stream().sorted(comparator).toList();
    }

    public Collection<TaskSortStrategy> available() {
        return strategies.values();
    }

    public TaskSortStrategy getDefaultStrategy() {
        return defaultStrategy;
    }
}
