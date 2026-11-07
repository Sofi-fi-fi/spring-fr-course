package com.taskplanner.repository;

import com.taskplanner.model.Priority;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPriorityRepository implements PriorityRepository {
    private final Map<Long, Priority> storage = new ConcurrentHashMap<>();

    public InMemoryPriorityRepository(@Qualifier("priorityIdSequence") SequenceGenerator sequence) {
        add(new Priority(sequence.next(), "LOW", "Низький", 1, "#6c757d"));
        add(new Priority(sequence.next(), "MEDIUM", "Середній", 2, "#0d6efd"));
        add(new Priority(sequence.next(), "HIGH", "Високий", 3, "#fd7e14"));
        add(new Priority(sequence.next(), "CRITICAL", "Критичний", 4, "#dc3545"));
    }

    private void add(Priority priority) {
        storage.put(priority.getId(), priority);
    }

    @Override
    public List<Priority> findAll() {
        return storage.values().stream()
                .sorted(Comparator.comparingInt(Priority::getWeight))
                .toList();
    }

    @Override
    public Optional<Priority> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Priority> findByCode(String code) {
        return storage.values().stream()
                .filter(p -> p.getCode().equalsIgnoreCase(code))
                .findFirst();
    }
}
