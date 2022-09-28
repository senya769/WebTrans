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
    User findByNickname(String nickname);
    User findByEmail(String email);
}
