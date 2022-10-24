package com.trans.repository;


import com.trans.model.Transport;
import com.trans.model.enums.TypeTransport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransportRepository extends JpaRepository<Transport,Integer> {
    List<Transport> findAll();
    List<Transport> findAllByUserId(int user_id);
    Transport findById(int id);
    void deleteById(int id);
    boolean deleteAllByUserId(int user_id);
    Page<Transport> findAllByType(TypeTransport type, Pageable pageable);
//        List<Transport> findAllByTypeAndFree(TypeTransport type,boolean isFree);
}
