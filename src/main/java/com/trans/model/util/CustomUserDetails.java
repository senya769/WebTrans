package com.trans.model.util;


import com.trans.model.Cargo;
import com.trans.model.Transport;
import com.trans.model.enums.Roles;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString
public class CustomUserDetails extends User {

    private int id;
    private String email;
    private Set<Roles> roles;

    public CustomUserDetails(UserDetails details, com.trans.model.User user) {
        super(details.getUsername(), details.getPassword(), details.getAuthorities());
        this.id = user.getId();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }

}
