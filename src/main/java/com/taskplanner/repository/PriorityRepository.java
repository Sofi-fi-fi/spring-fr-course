package com.taskplanner.repository;

import com.taskplanner.model.Priority;

import java.util.List;
import java.util.Optional;

public interface PriorityRepository {
    List<Priority> findAll();

    Optional<Priority> findById(Long id);

    Optional<Priority> findByCode(String code);
}
