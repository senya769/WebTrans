package com.trans.service.impl;

import com.trans.model.Order;
import com.trans.repository.OrderRepository;
import com.trans.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
