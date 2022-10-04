package com.trans.repository;


import com.trans.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String pas);
    User findById(int id);
    void deleteById(int id);
    List<User> findAll();
    User findByEmail(String email);
    User findByNumber(String number);
    User findByEmailOrNumber(String email, String number);
}
