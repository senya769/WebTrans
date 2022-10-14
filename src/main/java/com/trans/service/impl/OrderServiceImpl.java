package com.trans.service.impl;

import com.trans.model.Order;
import com.trans.repository.OrderRepository;
import com.trans.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAllByTransportUserId(int user_id) {
        return orderRepository.findAllByTransport_User_Id(user_id);
    }

    @Override
    public List<Order> findAllByCargoUserId(int user_id) {
        return orderRepository.findAllByCargo_User_Id(user_id);
    }

    @Override
    public Integer save(Order order) {
        Order save = orderRepository.save(order);
        return save.getId();
    }

    @Override
    public boolean removeById(Integer id) {
        Order orderForDelete = orderRepository.findById(id).orElse(null);
        if (orderForDelete != null) {
            orderRepository.delete(orderForDelete);
            return true;
        } else {
            return false;
        }
    }
}
