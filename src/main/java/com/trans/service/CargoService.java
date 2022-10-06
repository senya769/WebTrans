package com.trans.service;

import com.trans.dto.UserDTO;
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
    int saveWithUser(Cargo cargo, UserDTO user);
    List<Cargo> findAllSortByDateCreated();
    List<Cargo> findAll(int page,int count);
    List<Cargo> findAllSortByDateCreated(int page);
    void saveWithUserAndDate(Cargo cargo, UserDTO user, String dateDeadline);
    void save(Cargo cargo);
}
