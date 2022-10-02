package com.trans.model.util;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
@Setter
@ToString
public class CustomUserDetails extends User {

    private int id;
    private String email;
    private String nickname;

    public CustomUserDetails(UserDetails details, com.trans.model.User user) {
        super(details.getUsername(), details.getPassword(), details.getAuthorities());
        this.id = user.getId();
        this.email = user.getEmail();
       // this.nickname = user.getNickname();
    }


}
