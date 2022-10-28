package com.trans.repository;


import com.trans.model.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
    List<Cargo> findAllByUserId(int user_id);

    Cargo findById(int id);

    void deleteById(int id);

    void deleteAllByUserId(int user_id);

    /* @Query(value = "SELECT * FROM cargo ORDER BY date_created desc",nativeQuery = true)
     List<Cargo> findAllAsk();*/

    Page<Cargo> findAllByCityFromContaining(String cityFrom, boolean free, Pageable pageable);
}
