package com.trans.model.enums;

public enum TypeTransport {
    ANY("Any"),
    AUTO("Auto"),
    CARGO("Cargo"),
    CITY_TAXI("City taxi"),
    COUCH("Couch"),
    TRUCK("Truck");
    private final String typeValue;

    TypeTransport(String typeValue) {
        this.typeValue = typeValue;
    }

    public static TypeTransport fromString(String typeValue) {
        if (typeValue != null) {
            for (TypeTransport pt : TypeTransport.values()) {
                if (typeValue.equalsIgnoreCase(pt.typeValue)) {
                    return pt;
                }
            }
        }
        throw new IllegalArgumentException("No such value");
    }

    public String getTypeValue() {
        return typeValue;
    }
}
