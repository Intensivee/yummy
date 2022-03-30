package com.orange.mainservice.recipe;

public enum TimeType {
    TIME_1("15"),
    TIME_2("60"),
    TIME_3("120");

    final String value;

    TimeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value + " min.";
    }
}
