package com.taskplanner.controller;

import com.taskplanner.dto.TaskForm;
import com.taskplanner.exception.TaskValidationException;
import com.taskplanner.model.Task;
import com.taskplanner.model.TaskStatus;
import com.taskplanner.service.TaskService;
import com.taskplanner.service.sort.SortDirection;
import com.taskplanner.service.sort.TaskSorter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Clock;
import java.time.LocalDateTime;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final Clock clock;

    @Autowired
    private TaskSorter taskSorter;

    public TaskController(TaskService taskService, Clock clock) {
        this.taskService = taskService;
        this.clock = clock;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "priority") String sort,
                        @RequestParam(defaultValue = "desc") String dir,
                        Model model) {
        SortDirection direction = SortDirection.from(dir);
        model.addAttribute("tasks", taskService.findAll(sort, direction));
        model.addAttribute("sort", taskSorter.resolve(sort).key());
        model.addAttribute("dir", direction.name().toLowerCase());
        model.addAttribute("now", LocalDateTime.now(clock));
        return "index";
    }

    @GetMapping("/tasks/new")
    public String newTaskForm(Model model) {
        TaskForm form = new TaskForm();
        form.setPriorityCode(taskService.getDefaultPriorityCode());
        model.addAttribute("taskForm", form);
        return showForm(model, null);
    }

    @PostMapping("/tasks")
    public String create(@Valid @ModelAttribute("taskForm") TaskForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            return showForm(model, null);
        }
        try {
            Task task = taskService.create(form.toCommand());
            redirect.addFlashAttribute("message", "Задачу «" + task.getTitle() + "» створено");
            return "redirect:/";
        } catch (TaskValidationException e) {
            e.getErrors().forEach(error -> bindingResult.reject("task.invalid", error));
            return showForm(model, null);
        }
    }

    @GetMapping("/tasks/{id}/edit")
    public String editTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("taskForm", TaskForm.from(taskService.getById(id)));
        return showForm(model, id);
    }

    @PostMapping("/tasks/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("taskForm") TaskForm form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            return showForm(model, id);
        }
        try {
            Task task = taskService.update(id, form.toCommand());
            redirect.addFlashAttribute("message", "Задачу «" + task.getTitle() + "» збережено");
            return "redirect:/";
        } catch (TaskValidationException e) {
            e.getErrors().forEach(error -> bindingResult.reject("task.invalid", error));
            return showForm(model, id);
        }
    }

    @PostMapping("/tasks/{id}/complete")
    public String complete(@PathVariable Long id, RedirectAttributes redirect) {
        Task task = taskService.markCompleted(id);
        redirect.addFlashAttribute("message", "Задачу «" + task.getTitle() + "» виконано");
        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        Task task = taskService.getById(id);
        taskService.delete(id);
        redirect.addFlashAttribute("message", "Задачу «" + task.getTitle() + "» видалено");
        return "redirect:/";
    }

    private String showForm(Model model, Long taskId) {
        model.addAttribute("taskId", taskId);
        model.addAttribute("priorities", taskService.findAllPriorities());
        model.addAttribute("statuses", TaskStatus.values());
        return "task-form";
    }
}
