package com.trans.service;

import com.trans.model.Cargo;
import com.trans.model.User;

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
    List<Cargo> findAllSortByDateCreated(int page);
    int saveWithUserAndDate(Cargo cargo, User user, String dateDeadline);
}
