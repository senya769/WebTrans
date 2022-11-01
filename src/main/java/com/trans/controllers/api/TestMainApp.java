package com.trans.controllers.api;

import com.trans.model.Cargo;
import com.trans.model.enums.Roles;

import java.time.LocalDateTime;
import java.util.*;

public class TestMainApp {
    public static void main(String[] args) {
        LocalDateTime valCreate = LocalDateTime.of(2022,11,2,21,14,0);
        LocalDateTime valDeadline = LocalDateTime.now();
        boolean s = valCreate.isBefore(valDeadline);
        System.out.println(s);
    }
}
