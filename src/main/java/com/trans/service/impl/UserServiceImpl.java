package com.trans.service.impl;


import com.trans.model.User;
import com.trans.repository.UserRepository;
import com.trans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User findByEmailAndPassword(String e, String p) {
        return userRepository.findByEmailAndPassword(e, p);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
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
}
