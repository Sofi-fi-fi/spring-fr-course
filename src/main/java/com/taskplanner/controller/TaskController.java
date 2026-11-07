package com.taskplanner.controller;

import com.taskplanner.service.TaskCommand;
import com.taskplanner.service.TaskService;
import com.taskplanner.service.sort.SortDirection;
import com.taskplanner.service.sort.TaskSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class TaskController {
    private final TaskService taskService;

    @Autowired
    private TaskSorter taskSorter;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "priority") String sort,
                        @RequestParam(defaultValue = "desc") String dir,
                        Model model) {
        SortDirection direction = SortDirection.from(dir);
        model.addAttribute("tasks", taskService.findAll(sort, direction));
        model.addAttribute("priorities", taskService.findAllPriorities());
        model.addAttribute("sortOptions", taskSorter.available());
        model.addAttribute("sort", taskSorter.resolve(sort).key());
        model.addAttribute("dir", direction.name().toLowerCase());
        return "index";
    }

    @PostMapping("/tasks")
    public String create(@RequestParam String title,
                         @RequestParam(required = false) String description,
                         @RequestParam(required = false)
                         @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dueDate,
                         @RequestParam(required = false) String priorityCode) {
        taskService.create(new TaskCommand(title, description, dueDate, priorityCode));
        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/complete")
    public String complete(@PathVariable Long id) {
        taskService.markCompleted(id);
        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/delete")
    public String delete(@PathVariable Long id) {
        taskService.delete(id);
        return "redirect:/";
    }
}
