package com.trans.controllers.api;

import com.trans.model.Cargo;
import com.trans.model.enums.Roles;

import java.util.*;

public class TestMainApp {
    public static void main(String[] args) {
        Set<Roles> s = new HashSet<>();
        s.add(Roles.USER);

        boolean contains = s.contains(Roles.ADMIN);
        System.out.println(contains);
    }
}
