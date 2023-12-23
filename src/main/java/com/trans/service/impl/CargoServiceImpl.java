package com.trans.service.impl;

import com.trans.dto.UserDTO;
import com.trans.model.Cargo;
import com.trans.model.Order;
import com.trans.model.User;
import com.trans.model.enums.OrderStatus;
import com.trans.repository.CargoRepository;
import com.trans.repository.OrderRepository;
import com.trans.service.CargoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CargoServiceImpl implements CargoService {

    private final CargoRepository cargoRepository;
    private final OrderRepository orderRepository;
    @PersistenceContext
    private EntityManager em;


    private final ModelMapper modelMapper;

    @Autowired
    public CargoServiceImpl(CargoRepository cargoRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.cargoRepository = cargoRepository;
        this.orderRepository = orderRepository;

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
    public Page<Cargo> findAllByUserId(int user_id, int page) {
        return cargoRepository.findAllByUserId(user_id,
                PageRequest.of(page - 1, 8, Sort.by("localDateCreated").descending()));
    }

    @Override
    public Page<Cargo> findAllActiveByUserId(int user_id, int page) {
        return cargoRepository.findAllByUserIdAndDeleteIsNot(user_id,
                PageRequest.of(page - 1, 8, Sort.by("localDateCreated").descending()));
    }

    @Override
    public Cargo findById(int id) {
        return cargoRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        Cargo byId = findById(id);
        List<Order> allByCargoId = orderRepository.findAllByCargoId(id);
        boolean notReadyToDelete = allByCargoId.stream()
                .anyMatch(order -> order.getStatus() == OrderStatus.ACTIVE || order.getStatus() == OrderStatus.WAITING);
        if (notReadyToDelete) {
            return false;
        }

        if (allByCargoId.isEmpty()) {
            cargoRepository.deleteById(id);
        } else {
            byId.setDelete(true);
            cargoRepository.save(byId);
        }
        return true;
    }

    @Override
    public List<Cargo> findAllByDeleteIsFalseAndFreeIsTrue() {
        return cargoRepository.findAllByDeleteIsFalseAndFreeIsTrue();
    }

    @Override
    public boolean deleteAllByUserId(int user_id) {
        cargoRepository.deleteAllByUserId(user_id);
        return true;
    }

    @Override
    public int saveWithUser(Cargo cargo, UserDTO user) {
        cargo.setUser(modelMapper.map(user, User.class));
        return cargoRepository.save(cargo).getId();
    }

    @Override
    public void saveWithUserAndDate(Cargo cargo, UserDTO user, String dateDeadline) {
        cargo.setUser(modelMapper.map(user, User.class));
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
        if (cityFrom.equals("Any")) {
            return findAllSortByDateCreated(page);
        }
        return cargoRepository.findAllByCityFromContaining(cityFrom, PageRequest.of(page - 1, 8, Sort.by("localDateCreated").descending()));
    }

    @Override
    public Set<String> getDistinctCityFromCargo() {
        return cargoRepository.findAll().stream().map(Cargo::getCityFrom).collect(Collectors.toSet());
    }

    @Override
    public Page<Cargo> searchAllByKeyword(String keyword, int page) {
        if (keyword != null) {
            return cargoRepository
                    .searchAllByKeyword(keyword, PageRequest.of(page - 1, 8, Sort.by("localDateCreated").descending()));
        } else {
            return findAllSortByDateCreated(page);
        }
    }

    @Override
    public Page<Cargo> searchByArgs(int page, Object []args) {
        Pageable pageable = PageRequest.of(page-1,5,Sort.by("localDateCreated").descending());
        Specification<Cargo> specification =
                CargoRepository.cargoFilter(
                        (String) args[0],
                        (String) args[1],
                        (String) args[2],
                        (String) args[3],
                        (String) args[4],
                        (Double) args[5],
                        (Double) args[6],
                        (Double) args[7]);
        return cargoRepository.findAll(specification,pageable);
    }

    @Override
    public Page<Cargo> findAllSortByDateCreated(int page) {
        return cargoRepository
                .findAllByDeleteIsFalseAndFreeIsTrue(PageRequest.of(page - 1, 8, Sort.by("localDateCreated", "price").descending()));
    }
}
