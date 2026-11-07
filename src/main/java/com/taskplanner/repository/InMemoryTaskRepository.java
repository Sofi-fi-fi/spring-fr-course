package com.taskplanner.repository;

import com.taskplanner.model.Task;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTaskRepository implements TaskRepository {
    private final Map<Long, Task> storage = new ConcurrentHashMap<>();
    private final SequenceGenerator sequence;

    public InMemoryTaskRepository(@Qualifier("taskIdSequence") SequenceGenerator sequence) {
        this.sequence = sequence;
    }

    @Override
    public List<Task> findAll() {
        return storage.values().stream()
                .sorted(Comparator.comparing(Task::getId))
                .map(Task::copy)
                .toList();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(storage.get(id)).map(Task::copy);
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(sequence.next());
        }
        storage.put(task.getId(), task.copy());
        return task.copy();
    }

    @Override
    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
