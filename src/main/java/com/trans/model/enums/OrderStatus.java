package com.trans.model.enums;

public enum OrderStatus {
    WAITING("Waiting"),
    ACTIVE("Active"),
    COMPLETED("Completed"),
    REJECTED("Rejected");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public static OrderStatus fromString(String value) {
        if (value != null) {
            for (OrderStatus pt : OrderStatus.values()) {
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
