package com.trans.service.impl;

import com.trans.dto.UserDTO;
import com.trans.model.Cargo;
import com.trans.model.Order;
import com.trans.model.User;
import com.trans.model.enums.OrderStatus;
import com.trans.repository.CargoRepository;
import com.trans.service.CargoService;
import com.trans.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CargoServiceImpl implements CargoService {

    private final CargoRepository cargoRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CargoServiceImpl(CargoRepository cargoRepository, ModelMapper modelMapper) {
        this.cargoRepository = cargoRepository;

        this.modelMapper = modelMapper;
    }

    @Override
    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }
    public List<Cargo> findAll(Cargo cargo) {
        return cargoRepository.findAll(Example.of(cargo));
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
        Cargo byId = findById(id);
        boolean notReadyToDelete = byId.getOrderList().stream()
                .anyMatch(order -> order.getStatus() == OrderStatus.ACTIVE || order.getStatus() == OrderStatus.WAITING);
        if(notReadyToDelete){
            return false;
        }

        if(byId.getOrderList().isEmpty()){
        cargoRepository.deleteById(id);
        }else {
            byId.setDelete(true);
        }
        return true;
    }

    @Override
    public boolean deleteAllByUserId(int user_id) {
        cargoRepository.deleteAllByUserId(user_id);
        return true;
    }

    @Override
    public int saveWithUser(Cargo cargo, UserDTO user) {
        cargo.setUser(modelMapper.map(user,User.class));
        return cargoRepository.save(cargo).getId();
    }

    @Override
    public void saveWithUserAndDate(Cargo cargo, UserDTO user, String dateDeadline) {
        cargo.setUser(modelMapper.map(user,User.class));
        cargo.setLocalDateDeadline(LocalDateTime.parse(dateDeadline));
         cargoRepository.save(cargo);
    }

    @Override
    public void save(Cargo cargo) {
        cargoRepository.save(cargo);
    }

    @Override
    public List<Cargo> findAllSortByDateCreated() {
        return cargoRepository.findAll(Sort.by("localDateCreated", "price").descending());
    }


    @Override
    public List<Cargo> findAll(int page, int count) {
        Pageable pageable = PageRequest.of(page, count);
        return cargoRepository.findAll(pageable).getContent();
    }

    @Override
    public Page<Cargo> findAllByCityFromContaining(String cityFrom, Integer page) {
        if(cityFrom.equals("Any")){
            return findAllSortByDateCreated(page);
        }
        return cargoRepository.findAllByCityFromContaining(cityFrom, PageRequest.of(page-1,8,Sort.by("localDateCreated").descending()));
    }

    @Override
    public Set<String> getDistinctCityFromCargo() {
       return cargoRepository.findAll().stream().map(Cargo::getCityFrom).collect(Collectors.toSet());
    }

    @Override
    public Page<Cargo> searchAllByKeyword(String keyword, int page) {
        if(keyword!=null){
            return cargoRepository
                    .searchAllByKeyword(keyword, PageRequest.of(page-1,8,Sort.by("localDateCreated").descending()));
        }
        else {
           return findAllSortByDateCreated(page);
        }
    }

    @Override
    public Page<Cargo> findAllSortByDateCreated(int page) {
        return cargoRepository
                .findAll(PageRequest.of(page - 1, 8, Sort.by("localDateCreated", "price").descending()));
    }
}
