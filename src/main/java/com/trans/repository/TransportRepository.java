package com.trans.repository;


import com.trans.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransportRepository extends JpaRepository<Transport,Integer> {
    List<Transport> findAll();
    List<Transport> findAllByUserId(int user_id);
    Transport findTransportById(int id);
    boolean deleteTransportById(int id);
    Transport findTransportByUserId(int user_id);
//    int countTransport();
    boolean deleteAllByUserId(int user_id);
}
