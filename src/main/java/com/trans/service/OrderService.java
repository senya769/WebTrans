package com.trans.service;

import com.trans.model.Order;
import com.trans.repository.OrderRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface OrderService {
    List<Order> findAllByTransportUserId(int user_id);
    List<Order> findAllByCargoUserId(int user_id);
    Integer save(Order order);
    boolean removeById(Integer id);
}
