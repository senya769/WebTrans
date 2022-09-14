package com.trans.model;

public enum Roles {
    USER("User"),
    ADMIN("Admin"),
    MANAGER("Manager");
    private String name;

    Roles(String name) {
        this.name = name;
    }
}
