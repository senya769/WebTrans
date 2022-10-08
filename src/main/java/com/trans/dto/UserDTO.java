package com.trans.dto;

import com.trans.model.Cargo;
import com.trans.model.enums.Countries;
import com.trans.model.enums.Roles;
import com.trans.model.Transport;
import com.trans.model.enums.TypeActivity;
import com.trans.model.enums.TypeUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;
@Data
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String imageURL;
    private Countries country;
    private String city;
    private String number;
    private String email;
    private String password;
    private String nameCompany;
    private TypeActivity activity;
    private TypeUser type;
    @EqualsAndHashCode.Exclude
    private Set<Roles> roles;
    @EqualsAndHashCode.Exclude
    private List<Cargo> cargoList;
    @EqualsAndHashCode.Exclude
    private List<Transport> transportList;

}
