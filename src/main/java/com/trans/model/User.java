package com.trans.model;


import com.trans.model.enums.Countries;
import com.trans.model.enums.Roles;
import com.trans.model.enums.TypeActivity;
import com.trans.model.enums.TypeUser;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Objects;
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
    private Integer id;
    private String firstName;
    private String lastName;
    @Column(name = "image_url")
    private String imageURL = "/img/user/default.jpg";
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
    @Enumerated(EnumType.STRING)
    private TypeUser type;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Cargo> cargoList;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Transport> transportList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
