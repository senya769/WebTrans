package com.trans.service;

import com.trans.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllByTransportUserId(int user_id);
}
