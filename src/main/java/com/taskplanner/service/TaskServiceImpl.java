package com.taskplanner.service;

import com.taskplanner.model.Priority;
import com.taskplanner.model.Task;
import com.taskplanner.model.TaskStatus;
import com.taskplanner.repository.PriorityRepository;
import com.taskplanner.repository.TaskRepository;
import com.taskplanner.exception.TaskNotFoundException;
import com.taskplanner.exception.TaskValidationException;
import com.taskplanner.service.sort.SortDirection;
import com.taskplanner.service.sort.TaskSorter;
import com.taskplanner.validation.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final PriorityRepository priorityRepository;
    private final TaskSorter taskSorter;
    private final Clock clock;
    private final String defaultPriorityCode;

    private TaskValidator validator;

    public TaskServiceImpl(TaskRepository taskRepository,
                           PriorityRepository priorityRepository,
                           TaskSorter taskSorter,
                           Clock clock,
                           @Value("${app.tasks.default-priority:MEDIUM}") String defaultPriorityCode) {
        this.taskRepository = taskRepository;
        this.priorityRepository = priorityRepository;
        this.taskSorter = taskSorter;
        this.clock = clock;
        this.defaultPriorityCode = defaultPriorityCode;
    }

    @Autowired
    public void setValidator(TaskValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<Task> findAll(String sortKey, SortDirection direction) {
        return taskSorter.sort(taskRepository.findAll(), sortKey, direction);
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public Task create(TaskCommand command) {
        Task task = new Task();
        applyCommand(task, command);
        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(now());
        validator.validate(task);
        return taskRepository.save(task);
    }

    @Override
    public Task update(Long id, TaskCommand command) {
        Task task = getById(id);
        applyCommand(task, command);
        validator.validate(task);
        return taskRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        if (!taskRepository.deleteById(id)) {
            throw new TaskNotFoundException(id);
        }
    }

    @Override
    public Task markCompleted(Long id) {
        Task task = getById(id);
        if (task.isCompleted()) {
            return task;
        }
        task.setStatus(TaskStatus.DONE);
        task.setCompletedAt(now());
        return taskRepository.save(task);
    }

    @Override
    public List<Priority> findAllPriorities() {
        return priorityRepository.findAll();
    }

    private void applyCommand(Task task, TaskCommand command) {
        task.setTitle(command.title() == null ? null : command.title().strip());
        task.setDescription(command.description() == null || command.description().isBlank()
                ? null : command.description().strip());
        task.setDueDate(command.dueDate());
        task.setPriority(resolvePriority(command.priorityCode()));
    }

    private Priority resolvePriority(String code) {
        String effectiveCode = (code == null || code.isBlank()) ? defaultPriorityCode : code;
        return priorityRepository.findByCode(effectiveCode)
                .orElseThrow(() -> new TaskValidationException(
                        List.of("Невідомий пріоритет: " + effectiveCode)));
    }

    private LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
}
