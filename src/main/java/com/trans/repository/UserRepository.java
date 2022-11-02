package com.trans.repository;


import com.trans.model.Cargo;
import com.trans.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String pas);
    User findById(int id);
    void deleteById(int id);
    List<User> findAll();
    User findByEmail(String email);
    User findByNumber(String number);
    Optional<User> findByEmailOrNumber(String email, String number);
    List<User> findByEmailOrNumberOrderById(String email, String number);
}
