package com.trans.service.impl;


import com.trans.model.enums.Roles;
import com.trans.model.User;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final com.trans.repository.UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(com.trans.repository.UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmailAndPassword(String e, String p) {
        return userRepository.findByEmailAndPassword(e, p);
    }

    @Override
    public int save(User user) {
        if(user.getNameCompany() == null){
            setNameCompanyEI(user);
        }
        User userFromDb = this.findByEmailOrNumber(user.getEmail(),user.getNumber());
        if (userFromDb == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Set.of(Roles.USER));
            return userRepository.save(user).getId();
        } else {
            return 0;
        }
    }

    private void setNameCompanyEI(User user) {
        user.setNameCompany(new StringBuilder("EI \"")
                .append(user.getFirstName())
                .append(" ")
                .append(user.getLastName())
                .append("\"").toString());
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByNumber(String number) {
        return userRepository.findByNumber(number);
    }

    @Override
    public User findByEmailOrNumber(String email, String number) {
        return userRepository.findByEmailOrNumber(email, number);
    }
}
