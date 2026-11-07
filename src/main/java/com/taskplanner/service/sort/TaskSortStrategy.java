package com.taskplanner.service.sort;

import com.taskplanner.model.Task;

import java.util.Comparator;

public interface TaskSortStrategy {
    String key();

    String displayName();

    Comparator<Task> comparator();
}
