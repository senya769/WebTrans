package com.trans.model.enums;

public enum TypeActivity {
    INDIVIDUAL("Individual"), COMPANY("Company");
    private final String value;

    TypeActivity(String value) {
        this.value = value;
    }

    public static TypeActivity fromString(String value) {
        if (value != null) {
            for (TypeActivity pt : TypeActivity.values()) {
                if (value.equalsIgnoreCase(pt.value)) {
                    return pt;
                }
            }
        }
        throw new IllegalArgumentException("No such value");
    }

    public String getValue() {
        return value;
    }
}
