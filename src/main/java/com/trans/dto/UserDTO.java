package com.trans.dto;

import com.trans.model.Cargo;
import com.trans.model.enums.Roles;
import com.trans.model.Transport;
import lombok.Data;

import java.util.List;
import java.util.Set;
@Data
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String numberPhone;
    private String email;
    private String status;
    private Set<Roles> roles;
    private List<Cargo> cargoList;
    private List<Transport> transportList;
}
