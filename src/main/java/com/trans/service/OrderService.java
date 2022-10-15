package com.trans.service;

import com.trans.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllByTransportUserId(int user_id);
    List<Order> findAllByCargoUserId(int user_id);
    Integer save(Order order);
    boolean removeById(Integer id);
    // поиск отправленных заказов на транспорт
    List<Order> getTransportReceivedOrders(Integer transport_user_id, Integer customerId);

    // поиск полученных заказов на транспорт
    List<Order> getTransportSentOrders(Integer transport_user_id, Integer customerId);

    // поиск отправленных заказов на груз
    List<Order> getCargoReceivedOrders(Integer cargo_user_id, Integer customerId);

    // поиск полученных заказов на груз
    List<Order> getCargoSentOrders(Integer cargo_user_id, Integer customerId);
}
