package com.taskplanner.controller;

import com.taskplanner.service.report.BeanScopeDemoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeanDemoController {
    private final BeanScopeDemoService demoService;

    public BeanDemoController(BeanScopeDemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/beans-demo")
    public String beansDemo(Model model) {
        model.addAttribute("rows", demoService.describe());
        model.addAttribute("primaryStrategy", demoService.primaryStrategyName());
        return "beans-demo";
    }
}
