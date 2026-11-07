package com.taskplanner.service;

import com.taskplanner.model.TaskStatus;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DemoDataLoader implements ApplicationRunner {
    private final TaskService taskService;
    private final Clock clock;

    public DemoDataLoader(TaskService taskService, Clock clock) {
        this.taskService = taskService;
        this.clock = clock;
    }

    @Override
    public void run(ApplicationArguments args) {
        LocalDate today = LocalDate.now(clock);
        LocalDateTime evening = today.atTime(LocalTime.of(18, 0));

        taskService.create(new TaskCommand("Здати лабораторну роботу №3",
                "Використання шаблонізатора Thymeleaf", evening.plusDays(2), "HIGH", null));
        taskService.create(new TaskCommand("Підготуватися до контрольної з БД",
                "Нормальні форми, транзакції, індекси", evening.plusDays(5), "CRITICAL", TaskStatus.IN_PROGRESS));
        taskService.create(new TaskCommand("Купити продукти", null, evening, "LOW", null));
        taskService.create(new TaskCommand("Оплатити інтернет",
                "До кінця місяця", evening.minusDays(1), "MEDIUM", null));
        taskService.create(new TaskCommand("Прочитати розділ про IoC-контейнер", null, null, "MEDIUM", null));

        var done = taskService.create(new TaskCommand("Встановити JDK 25 та IntelliJ IDEA",
                null, evening.minusDays(3), "HIGH", null));
        taskService.markCompleted(done.getId());
    }
}
