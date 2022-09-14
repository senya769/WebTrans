package com.trans.service.impl;

import com.trans.model.Cargo;
import com.trans.repository.CargoRepository;
import com.trans.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CargoServiceImpl implements CargoService {

    private final CargoRepository repository;

    @Autowired
    public CargoServiceImpl(CargoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cargo> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Cargo> findAllByUserId(int user_id) {
        return repository.findAllByUserId(user_id);
    }

    @Override
    public Cargo findCargoById(int id) {
        return repository.findCargoById(id);
    }

    @Override
    public boolean deleteCargoById(int id) {
        return repository.deleteCargoById(id);
    }

    @Override
    public Cargo findCargoByUserId(int user_id) {
        return repository.findCargoByUserId(user_id);
    }

    @Override
    public int countCargo() {
        //  return repository.countAll();
        return 1;
    }

    @Override
    public boolean deleteAllByUserId(int user_id) {
        return repository.deleteAllByUserId(user_id);
    }

    @Override
    public void save(Cargo cargo) {
        repository.save(cargo);
    }
}
