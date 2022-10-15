package com.trans.model.enums;

public enum TypeUser {
    CARRIER("Carrier"),CUSTOMER("Customer");
    private final String value;

    TypeUser(String value) {
        this.value = value;
    }

    public static TypeUser fromString(String value) {
        if (value != null) {
            for (TypeUser pt : TypeUser.values()) {
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
