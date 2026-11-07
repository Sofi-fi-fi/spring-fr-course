package com.taskplanner.service.report;

import com.taskplanner.service.TaskService;
import com.taskplanner.service.sort.TaskSortStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeanScopeDemoService {
    private final ApplicationContext context;

    private final TaskReport injectedReport;

    private final ObjectProvider<TaskReport> reportProvider;
    private final TaskSortStrategy primaryStrategy;

    public BeanScopeDemoService(ApplicationContext context,
                                TaskReport injectedReport,
                                ObjectProvider<TaskReport> reportProvider,
                                TaskSortStrategy primaryStrategy) {
        this.context = context;
        this.injectedReport = injectedReport;
        this.reportProvider = reportProvider;
        this.primaryStrategy = primaryStrategy;
    }

    public List<BeanScopeRow> describe() {
        return List.of(
                row("TaskService — context.getBean() двічі", "singleton",
                        context.getBean(TaskService.class), context.getBean(TaskService.class)),
                row("TaskReport — context.getBean() двічі", "prototype",
                        context.getBean(TaskReport.class), context.getBean(TaskReport.class)),
                row("TaskReport, впроваджений у singleton через конструктор", "prototype у singleton",
                        injectedReport, injectedReport),
                row("TaskReport через ObjectProvider.getObject() двічі", "prototype у singleton (рішення)",
                        reportProvider.getObject(), reportProvider.getObject())
        );
    }

    public String primaryStrategyName() {
        return primaryStrategy.getClass().getSimpleName();
    }

    private static BeanScopeRow row(String description, String scope, Object first, Object second) {
        return new BeanScopeRow(description, scope, hex(first), hex(second), first == second);
    }

    private static String hex(Object o) {
        return "@" + Integer.toHexString(System.identityHashCode(o));
    }

    public record BeanScopeRow(String description, String scope, String first, String second, boolean sameInstance) {
    }
}
