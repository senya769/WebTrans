package com.trans.service;

import com.trans.dto.UserDTO;
import com.trans.model.Transport;
import com.trans.model.User;

import java.util.List;

public interface TransportService {
    List<Transport> findAll();
    List<Transport> findAllByUserId(int user_id);
    Transport findById(int id);
    boolean deleteById(int id);
    boolean deleteAllByUserId(int user_id);
    void save(Transport transport);
    void saveWithUser(Transport transport, User user);
   /* List<Cargo> findAllSortByDateCreated();
    List<Cargo> findAll(int page,int count);
    List<Cargo> findAllSortByDateCreated(int page);*/
}
