package com.trans.service.impl;

import com.trans.model.Order;
import com.trans.model.enums.OrderStatus;
import com.trans.repository.OrderRepository;
import com.trans.service.CargoService;
import com.trans.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    public Page<Order> findAllByTransportUserId(int user_id,int page) {
        return orderRepository.findAllByTransport_User_Id(user_id,
                PageRequest.of(page-1,8, Sort.by("localDateCreated").descending()));
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
        order.setLocalDateCreated(LocalDateTime.now());
        order.getCargo().setFree(false);
        order.getTransport().setFree(false);
        Order save = orderRepository.save(order);
        return save.getId();
    }

    @Override
    public Integer cancel(Order order) {
        order.setStatus(OrderStatus.REJECTED);
        order.setLocalDateCreated(LocalDateTime.now());
        order.getCargo().setFree(true);
        order.getTransport().setFree(true);
        return orderRepository.save(order).getId();
    }

    @Override
    public Integer complete(Order order) {
        order.setStatus(OrderStatus.COMPLETED);
        order.setLocalDateCreated(LocalDateTime.now());
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
    public Page<Order> getTransportReceivedOrdersById(Integer customerId,int page) {
        List<Order> localDateCreated;
        if(page == 0){
            localDateCreated = orderRepository.findByTransport_User_IdAndCustomerIdNotAndTransport_Delete(customerId, customerId, false
                            , PageRequest.of(0, Integer.MAX_VALUE, Sort.by("localDateCreated").descending()))
                    .stream()
                    .filter(order -> order.getStatus() == OrderStatus.ACTIVE || order.getStatus() == OrderStatus.WAITING).toList();
        }else{
            localDateCreated = orderRepository.findByTransport_User_IdAndCustomerIdNotAndTransport_Delete(customerId, customerId, false,
                            PageRequest.of(page - 1, 8, Sort.by("localDateCreated").descending()))
                    .stream()
                    .filter(order -> order.getStatus() == OrderStatus.ACTIVE || order.getStatus() == OrderStatus.WAITING).toList();
        }
        return new PageImpl<>(localDateCreated);

    }

    @Override
    public Page<Order> getTransportSentOrdersById(Integer customerId,int page) {
        List<Order> localDateCreated;
        if(page == 0){
            localDateCreated = orderRepository.findByTransport_User_IdAndCustomerIdAndTransport_Delete(customerId, customerId, false
                            , PageRequest.of(0, Integer.MAX_VALUE, Sort.by("localDateCreated").descending()))
                    .stream()
                    .filter(order -> order.getStatus() == OrderStatus.ACTIVE || order.getStatus() == OrderStatus.WAITING).toList();
        }else{
            localDateCreated = orderRepository.findByTransport_User_IdAndCustomerIdAndTransport_Delete(customerId, customerId, false,
                            PageRequest.of(page - 1, 8, Sort.by("localDateCreated").descending()))
                    .stream()
                    .filter(order -> order.getStatus() == OrderStatus.ACTIVE || order.getStatus() == OrderStatus.WAITING).toList();
        }
        return new PageImpl<>(localDateCreated);
    }

    @Override
    public Page<Order> getCargoReceivedOrdersById(Integer customerId,int page) {
        List<Order> localDateCreated;
        if(page == 0){
            localDateCreated = orderRepository.findByCargo_User_IdAndCustomerIdNotAndCargo_Delete(customerId, customerId, false
                            , PageRequest.of(0, Integer.MAX_VALUE, Sort.by("localDateCreated").descending()))
                    .stream()
                    .filter(order -> order.getStatus() == OrderStatus.ACTIVE || order.getStatus() == OrderStatus.WAITING).toList();
        }else{
            localDateCreated = orderRepository.findByCargo_User_IdAndCustomerIdNotAndCargo_Delete(customerId, customerId, false,
                            PageRequest.of(page - 1, 8, Sort.by("localDateCreated").descending()))
                    .stream()
                    .filter(order -> order.getStatus() == OrderStatus.ACTIVE || order.getStatus() == OrderStatus.WAITING).toList();
        }
        return new PageImpl<>(localDateCreated);
    }


    @Override
    public Page<Order> getCargoSentOrdersById(Integer customerId,int page) {
        List<Order> localDateCreated;
        if(page == 0){
            localDateCreated = orderRepository.findByCargo_User_IdAndCustomerId_AndCargo_Delete(customerId, customerId, false
                            , PageRequest.of(0, Integer.MAX_VALUE, Sort.by("localDateCreated").descending()))
                    .stream()
                    .filter(order -> order.getStatus() == OrderStatus.ACTIVE || order.getStatus() == OrderStatus.WAITING).toList();
        } else {
            localDateCreated = orderRepository.findByCargo_User_IdAndCustomerId_AndCargo_Delete(customerId, customerId, false,
                            PageRequest.of(page - 1, 8, Sort.by("localDateCreated").descending()))
                    .stream()
                    .filter(order -> order.getStatus() == OrderStatus.ACTIVE || order.getStatus() == OrderStatus.WAITING).toList();
        }
        return new PageImpl<>(localDateCreated);
    }

    @Override
    public Page<Order> findByTransportForLoggerInfo(Integer transport_user_id,int page) {
        List<Order> localDateCreated = orderRepository.findAllByTransport_User_Id(transport_user_id,
                        PageRequest.of(page - 1, 8, Sort.by("localDateCreated").descending()))
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.REJECTED || order.getStatus() == OrderStatus.COMPLETED)
                .toList();
        return new PageImpl<>(localDateCreated);
    }

    @Override
    public Page<Order> findByCargoForLoggerInfo(Integer cargo_user_id, int page) {
        List<Order> localDateCreated = orderRepository.findAllByCargo_User_Id(cargo_user_id,
                        PageRequest.of(page - 1, 8, Sort.by("localDateCreated").descending()))
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.REJECTED || order.getStatus() == OrderStatus.COMPLETED)
                .toList();
        return new PageImpl<>(localDateCreated);
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }
}
