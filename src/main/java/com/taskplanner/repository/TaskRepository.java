package com.taskplanner.repository;

import com.taskplanner.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();

    Optional<Task> findById(Long id);

    Task save(Task task);

    boolean deleteById(Long id);

    boolean existsById(Long id);
}
