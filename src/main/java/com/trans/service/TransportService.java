package com.trans.service;

import com.trans.model.Transport;
import com.trans.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransportService {
    List<Transport> findAll();
    List<Transport> findAllByUserId(int user_id);
    Transport findById(int id);
    boolean deleteById(int id);
    boolean deleteAllByUserId(int user_id);
    void save(Transport transport);
    void saveWithUser(Transport transport, User user);
    Page<Transport> findAll(int page);
    Page<Transport> findAllByType(String type, Integer page);
   /* List<Cargo> findAllSortByDateCreated();
    List<Cargo> findAll(int page,int count);
    List<Cargo> findAllSortByDateCreated(int page);*/
}
