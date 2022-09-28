package com.trans.repository;


import com.trans.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CargoRepository extends JpaRepository<Cargo,Integer> {
    List<Cargo> findAll();
    List<Cargo> findAllByUserId(int user_id);
    Cargo findCargoById(int id);
    void deleteCargoById(int id);
    Cargo findCargoByUserId(int user_id);
    void deleteAllByUserId(int user_id);
    @Query(value = "SELECT * FROM cargo ORDER BY date_created desc",nativeQuery = true)
    List<Cargo> findAllCargoAsk();
    /*List<Cargo> findAllByCountry_from();
    List<Cargo> findAllByCountry_to();
    List<Cargo> findAllByName(String name);
    List<Cargo> findAllByLocalDateCreated(LocalDateTime localDateCreated);
    List<Cargo> findAllByLocalDateDeadline(LocalDateTime localDateDeadline);*/
}
