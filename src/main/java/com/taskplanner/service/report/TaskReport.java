package com.taskplanner.service.report;

import com.taskplanner.model.Task;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskReport {
    private final LocalDateTime generatedAt;
    private int total;
    private int completed;

    public TaskReport(Clock clock) {
        this.generatedAt = LocalDateTime.now(clock);
    }

    public void add(Task task) {
        total++;
        if (task.isCompleted()) {
            completed++;
        }
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public int getTotal() {
        return total;
    }

    public int getCompleted() {
        return completed;
    }
}
