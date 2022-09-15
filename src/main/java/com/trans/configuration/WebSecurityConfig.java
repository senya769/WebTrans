package com.trans.configuration;

import com.trans.repository.UserRepository;
import com.trans.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public WebSecurityConfig(AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(req -> req.antMatchers("/login", "/add", "/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").failureForwardUrl("/login").permitAll())
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler));
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl(userRepository);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManagerBuilder managerBuilder(HttpSecurity http) throws Exception {
        return (AuthenticationManagerBuilder) http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService()).and()
               .build();
    }
}
