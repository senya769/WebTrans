package com.trans.service;

import com.trans.model.Order;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface OrderService {
    List<Order> findAllByTransportUserId(int user_id);
    List<Order> findAllByCargoUserId(int user_id);
    Integer save(Order order);
    Integer accept(Order order) throws SQLIntegrityConstraintViolationException;
    Integer cancel(Order order);
    Integer complete(Order order);
    boolean removeById(Integer id);
    // поиск отправленных заказов на транспорт
    List<Order> getTransportReceivedOrdersById( Integer customerId);

    // поиск полученных заказов на транспорт
    List<Order> getTransportSentOrdersById(Integer customerId);

    // поиск отправленных заказов на груз
    List<Order> getCargoReceivedOrdersById(Integer customerId);

    // поиск полученных заказов на груз
    List<Order> getCargoSentOrdersById( Integer customerId);
    //find by id
    Order findById(Integer id);

}
