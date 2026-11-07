package com.taskplanner.config;

import com.taskplanner.repository.SequenceGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class AppConfig {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public SequenceGenerator taskIdSequence() {
        return new SequenceGenerator("task_seq", 1);
    }

    @Bean
    public SequenceGenerator priorityIdSequence() {
        return new SequenceGenerator("priority_seq", 1);
    }
}
