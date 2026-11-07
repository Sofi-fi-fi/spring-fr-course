package com.taskplanner.service;

import com.taskplanner.model.Priority;
import com.taskplanner.model.Task;
import com.taskplanner.service.sort.SortDirection;

import java.util.List;

public interface TaskService {
    List<Task> findAll(String sortKey, SortDirection direction);

    Task getById(Long id);

    Task create(TaskCommand command);

    Task update(Long id, TaskCommand command);

    void delete(Long id);

    Task markCompleted(Long id);

    List<Priority> findAllPriorities();
}
