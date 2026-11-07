package com.taskplanner.model;

import java.util.Objects;

public class Priority {
    private Long id;
    private String code;
    private String name;
    private int weight;
    private String color;

    public Priority() {
    }

    public Priority(Long id, String code, String name, int weight, String color) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Priority other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Priority{id=" + id + ", code='" + code + "', weight=" + weight + '}';
    }
}
