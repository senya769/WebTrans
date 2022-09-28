package com.trans.service;

import com.trans.model.Cargo;

import java.util.List;

public interface CargoService {
    List<Cargo> findAll();
    List<Cargo> findAllByUserId(int user_id);
    Cargo findCargoById(int id);
    boolean deleteCargoById(int id);
    Cargo findCargoByUserId(int user_id);
    int countCargo();
    boolean deleteAllByUserId(int user_id);
    void save(Cargo cargo);
    List<Cargo> findAllCargoAsk();
}
