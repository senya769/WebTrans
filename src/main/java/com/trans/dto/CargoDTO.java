package com.trans.dto;


import com.trans.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CargoDTO {
    private double price;
    private String weight;
    private String length;
    private String height;
    private String country_from;
    private String country_to;
    private LocalDateTime localDateDeadline;
    private LocalDateTime localDateCreated = LocalDateTime.now();
    private User user;
}
