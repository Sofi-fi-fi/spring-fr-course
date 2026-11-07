# Планувальник задач (Spring Framework)

Лабораторні роботи з дисципліни «Розроблення застосунків з використанням Spring Framework».
Варіант 20 — **Планувальник задач**.

| | |
|---|---|
| Сутності | Задача (`Task`), дата (`dueDate`, `createdAt`, `completedAt`), пріоритет (`Priority`) |
| Актор | Користувач |
| Сценарії | створення / редагування / видалення задачі, відмітка «виконана», сортування за пріоритетом чи датою |

## Стек
Java 25 · Gradle 9 · Spring Boot 4.1 · Thymeleaf · PostgreSQL (з ЛР5)

## Гілки
| Гілка | Зміст |
|---|---|
| `main` | ЛР2 → ЛР3 → ЛР4 (сховище в пам'яті) |
| `lab5` | ЛР5: PostgreSQL, JdbcTemplate / JdbcClient, транзакції |
| `lab6` | ЛР6: Spring Data JPA |

## Запуск
```bash
./gradlew bootRun        # Windows: gradlew.bat bootRun
```
Застосунок: http://localhost:8080 · демонстрація бінів: http://localhost:8080/beans-demo

## Структура
```
src/main/java/com/taskplanner/
├─ config/       @Configuration та @Bean
├─ controller/   контролери
├─ exception/    винятки бізнес-логіки
├─ model/        Task, Priority, TaskStatus
├─ repository/   інтерфейси DAO та їх реалізації
├─ service/      бізнес-логіка (sort/, report/)
└─ validation/   перевірка бізнес-правил
src/main/resources/
├─ templates/    Thymeleaf-шаблони
└─ static/css/
```
