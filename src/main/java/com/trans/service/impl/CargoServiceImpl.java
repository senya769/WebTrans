package com.trans.service.impl;

import com.trans.model.Cargo;
import com.trans.model.User;
import com.trans.repository.CargoRepository;
import com.trans.service.CargoService;
import com.trans.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CargoServiceImpl implements CargoService {

    private final CargoRepository cargoRepository;

    @Autowired
    public CargoServiceImpl(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Override
    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    @Override
    public List<Cargo> findAllByUserId(int user_id) {
        return cargoRepository.findAllByUserId(user_id);
    }

    @Override
    public Cargo findById(int id) {
        return cargoRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        cargoRepository.deleteById(id);
        return true;
    }


    @Override
    public boolean deleteAllByUserId(int user_id) {
        cargoRepository.deleteAllByUserId(user_id);
        return true;
    }

    @Override
    public int saveWithUser(Cargo cargo, User user) {
        cargo.setUser(user);
        return cargoRepository.save(cargo).getId();
    }
    @Override
    public int saveWithUserAndDate(Cargo cargo, User user, String dateDeadline) {
        cargo.setUser(user);
        cargo.setLocalDateDeadline(LocalDateTime.parse(dateDeadline));
        return cargoRepository.save(cargo).getId();
    }

    @Override
    public List<Cargo> findAllSortByDateCreated() {
//        return repository.findAllAsk();
        return cargoRepository.findAll(Sort.by("localDateCreated", "price").descending());
    }


    @Override
    public List<Cargo> findAll(int page, int count) {
        Pageable pageable = PageRequest.of(page, count);
        return cargoRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Cargo> findAllSortByDateCreated(int page) {
        return cargoRepository
                .findAll(PageRequest.of(page - 1, 5, Sort.by("localDateCreated", "price").descending()))
                .getContent();
    }
}
