package com.trans.dto;

import com.trans.model.Cargo;
import com.trans.model.enums.Countries;
import com.trans.model.enums.Roles;
import com.trans.model.Transport;
import com.trans.model.enums.TypeActivity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
@Data
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String imageURL;
    private Countries country;
    private String city;
    private String number;
    private String email;
    private String nameCompany;
    private TypeActivity activity;
    private Set<Roles> roles;
    private List<Cargo> cargoList;
    private List<Transport> transportList;

}
