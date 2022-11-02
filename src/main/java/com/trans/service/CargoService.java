package com.trans.service;

import com.trans.dto.UserDTO;
import com.trans.model.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface CargoService {
    List<Cargo> findAll();
    List<Cargo> findAll(Cargo cargo);
    Page<Cargo> findAllByUserId(int user_id,int page);
    List<Cargo> findAllActiveByUserId(int user_id,int page);
    Cargo findById(int id);
    boolean deleteById(int id);
    List<Cargo>findAllByDeleteIsFalseAndFreeIsTrue();
    boolean deleteAllByUserId(int user_id);
    int saveWithUser(Cargo cargo, UserDTO user);
    List<Cargo> findAllSortByDateCreated();
    List<Cargo> findAll(int page,int count);
    Page<Cargo> findAllSortByDateCreated(int page);
    void saveWithUserAndDate(Cargo cargo, UserDTO user, String dateDeadline);
    void save(Cargo cargo);
    Page<Cargo> findAllByCityFromContaining(String cityFrom,Integer page);
    Set<String> getDistinctCityFromCargo();
    Page<Cargo> searchAllByKeyword(String keyword,int page);
}
