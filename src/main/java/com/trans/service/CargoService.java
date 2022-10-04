package com.trans.service;

import com.trans.model.Cargo;
import com.trans.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CargoService {
    List<Cargo> findAll();
    List<Cargo> findAllByUserId(int user_id);
    Cargo findById(int id);
    boolean deleteById(int id);
    boolean deleteAllByUserId(int user_id);
    int saveWithUser(Cargo cargo, User user);
    List<Cargo> findAllSortByDateCreated();
    List<Cargo> findAll(int page,int count);
    Page<Cargo> findAllSortByDateCreated(int page);
    void saveWithUserAndDate(Cargo cargo, User user, String dateDeadline);
    void save(Cargo cargo);
}
