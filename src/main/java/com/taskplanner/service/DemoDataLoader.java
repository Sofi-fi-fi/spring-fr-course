package com.taskplanner.service;

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

        taskService.create(new TaskCommand("Здати лабораторну роботу №2",
                "Створення та зв'язування компонентів Spring", evening.plusDays(2), "HIGH"));
        taskService.create(new TaskCommand("Підготуватися до контрольної з БД",
                "Нормальні форми, транзакції, індекси", evening.plusDays(5), "CRITICAL"));
        taskService.create(new TaskCommand("Купити продукти", null, evening, "LOW"));
        taskService.create(new TaskCommand("Оплатити інтернет",
                "До кінця місяця", evening.minusDays(1), "MEDIUM"));
        taskService.create(new TaskCommand("Прочитати розділ про IoC-контейнер", null, null, "MEDIUM"));

        var done = taskService.create(new TaskCommand("Встановити JDK 25 та IntelliJ IDEA",
                null, evening.minusDays(3), "HIGH"));
        taskService.markCompleted(done.getId());
    }
}
