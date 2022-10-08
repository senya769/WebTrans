package com.trans.service.impl;


import com.trans.dto.UserDTO;
import com.trans.model.enums.Roles;
import com.trans.model.User;
import com.trans.repository.UserRepository;
import com.trans.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO findByEmailAndPassword(String e, String p) {
        User userFromDb = userRepository.findByEmailAndPassword(e, p);
        return modelMapper.map(userFromDb, UserDTO.class);
    }

    @Override
    public int save(User user) {
        if (user.getNameCompany() == null) {
            setNameCompanyEI(user);
        }
        User userFromDb = userRepository.findByEmailOrNumber(user.getEmail(), user.getNumber()).orElse(null);
        if (userFromDb == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Set.of(Roles.USER));
            return userRepository.save(user).getId();
        } else {
            return 0;
        }
    }

    //remake
    @Override
    public int save(UserDTO user) {
        return userRepository.save(modelMapper.map(user, User.class)).getId();
    }

    private User setNameCompanyEI(User user) {
        user.setNameCompany(new StringBuilder("EI \"")
                .append(user.getFirstName())
                .append(" ")
                .append(user.getLastName())
                .append("\"").toString()
        );
        return user;
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(int id) {
        userRepository.save(userRepository.findById(id));
    }

    @Override
    public boolean update(UserDTO userDTO, String password) {
        User userWithPassword = userRepository.findById(userDTO.getId().intValue());
        User userFromDB = userRepository.findByEmailOrNumber(userDTO.getEmail(), userDTO.getNumber()).orElse(null);
        if ((userFromDB == null || userFromDB.getId() == userWithPassword.getId())&&
                passwordEncoder.matches(password, userWithPassword.getPassword())) {
            User user = modelMapper.map(userDTO, User.class);
            user.setPassword(userWithPassword.getPassword());
            userRepository.save(setNameCompanyEI(user));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDTO findDTOById(int id) {
        User userFromDb = userRepository.findById(id);
        return modelMapper.map(userFromDb, UserDTO.class);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(p -> modelMapper.map(p, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO findByNumber(String number) {
        User userDB = userRepository.findByNumber(number);
        return modelMapper.map(userDB, UserDTO.class);
    }

    @Override
    public UserDTO findByEmailOrNumber(String email, String number) {
        User userFromDB = userRepository.findByEmailOrNumber(email, number).orElse(null);
        UserDTO map = modelMapper.map(userFromDB, UserDTO.class);
        return map;
    }
}
