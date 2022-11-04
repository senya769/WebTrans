package com.trans.service;

import com.trans.model.Order;
import org.springframework.data.domain.Page;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface OrderService {
    Page<Order> findAllByTransportUserId(int user_id,int page);
    List<Order> findAllByCargoUserId(int user_id);
    Integer save(Order order);
    Integer accept(Order order) throws SQLIntegrityConstraintViolationException;
    Integer cancel(Order order);
    Integer complete(Order order);
    boolean removeById(Integer id);
    // поиск отправленных заказов на транспорт
    Page<Order> getTransportReceivedOrdersById(Integer customerId,int page);

    // поиск полученных заказов на транспорт
    Page<Order> getTransportSentOrdersById(Integer customerId,int page);

    // поиск отправленных заказов на груз
    Page<Order> getCargoReceivedOrdersById(Integer customerId,int page);

    // поиск полученных заказов на груз
    Page<Order> getCargoSentOrdersById(Integer customerId,int page);
    // список
    Page<Order> findByTransportForLoggerInfo(Integer transport_user_id,int page);
    Page<Order> findByCargoForLoggerInfo(Integer transport_user_id,int page);
    //find by id
    Order findById(Integer id);

}
