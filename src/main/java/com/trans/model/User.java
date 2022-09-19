package com.trans.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@ToString
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nickname;
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;
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
