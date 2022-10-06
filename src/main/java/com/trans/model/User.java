package com.trans.model;


import com.trans.model.enums.Countries;
import com.trans.model.enums.Roles;
import com.trans.model.enums.TypeActivity;
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
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    @Column(name = "image_url")
    private String imageURL;
    @Enumerated(EnumType.STRING)
    private Countries country;
    private String city;
    @Column(unique = true)
    private String number;
    @Column(unique = true)
    private String email;
    private String password;
    private String nameCompany;
    @Enumerated(EnumType.STRING)
    private TypeActivity activity;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Cargo> cargoList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Transport> transportList;

}
