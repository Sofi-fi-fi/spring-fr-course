package com.taskplanner.repository;

import java.util.concurrent.atomic.AtomicLong;

public class SequenceGenerator {
    private final String name;
    private final AtomicLong counter;

    public SequenceGenerator(String name, long startWith) {
        this.name = name;
        this.counter = new AtomicLong(startWith);
    }

    public long next() {
        return counter.getAndIncrement();
    }

    public String getName() {
        return name;
    }
}
