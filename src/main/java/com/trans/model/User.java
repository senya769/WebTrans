package com.trans.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Roles roles;
    //    private String role;
    @Column(name = "number")
    private String numberPhone;
    private String email;
    private String password;
    private String status;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Cargo> cargoList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Transport> transportList;

}
