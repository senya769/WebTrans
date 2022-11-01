package com.trans.service.impl;

import com.trans.model.Order;
import com.trans.model.enums.OrderStatus;
import com.trans.repository.OrderRepository;
import com.trans.service.CargoService;
import com.trans.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CargoService cargoService;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CargoService cargoService) {
        this.orderRepository = orderRepository;
        this.cargoService = cargoService;
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
        order.setLocalDateCreated(LocalDateTime.now());
        Order save = orderRepository.save(order);
        return save.getId();
    }

    @Override
    public Integer accept(Order order) {
        order.setStatus(OrderStatus.ACTIVE);
        order.getCargo().setFree(false);
        order.getTransport().setFree(false);
        Order save = orderRepository.save(order);
        return save.getId();
    }

    @Override
    public Integer cancel(Order order) {
        order.setStatus(OrderStatus.REJECTED);
        order.getCargo().setFree(true);
        order.getTransport().setFree(true);
        return orderRepository.save(order).getId();
    }

    @Override
    public Integer complete(Order order) {
        order.setStatus(OrderStatus.COMPLETED);
        order.getTransport().setFree(true);
        cargoService.deleteById(order.getCargo().getId());
        return orderRepository.save(order).getId();
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

    @Override
    public List<Order> getTransportReceivedOrdersById(Integer customerId) {
        return orderRepository.findByTransport_User_IdAndCustomerIdNotAndTransport_Delete(customerId, customerId, false);
        // stream.filter()

    }

    @Override
    public List<Order> getTransportSentOrdersById(Integer customerId) {
        return orderRepository.findByTransport_User_IdAndCustomerIdAndTransport_Delete(customerId, customerId, false);
// stream.filter()

    }

    @Override
    public List<Order> getCargoReceivedOrdersById(Integer customerId) {
        return orderRepository.findByCargo_User_IdAndCustomerIdNotAndCargo_Delete(customerId, customerId, false);
// stream.filter()
    }

    @Override
    public List<Order> getCargoSentOrdersById(Integer customerId) {
        return orderRepository.findByCargo_User_IdAndCustomerId_AndCargo_Delete(customerId, customerId, false);
// stream.filter()

    }

    @Override
    public List<Order> findByTransportForLoggerInfo(Integer transport_user_id) {
        return orderRepository.findAllByTransport_User_Id(transport_user_id).stream()
                .filter(order -> order.getStatus() == OrderStatus.REJECTED || order.getStatus() == OrderStatus.COMPLETED)
                .toList();
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }
}
