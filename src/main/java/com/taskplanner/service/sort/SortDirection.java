package com.taskplanner.service.sort;

public enum SortDirection {
    ASC, DESC;

    public static SortDirection from(String value) {
        return "desc".equalsIgnoreCase(value) ? DESC : ASC;
    }
}
