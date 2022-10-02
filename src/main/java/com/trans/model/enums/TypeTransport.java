package com.trans.model.enums;

public enum TypeTransport {
    AUTO("Auto"),
    CARGO("Cargo"),
    CITY_TAXI("City taxi"),
    COUCH("Coach Bus"),
    TRUCK("Truck");
    private final String typeValue;

    TypeTransport(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeValue() {
        return typeValue;
    }
}
