package com.trans.repository;

import com.trans.model.Cargo;
import com.trans.model.Order;
import com.trans.model.Transport;
import com.trans.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    //List<Order> findAllByTransportUserId(int transport_user_id);
    List<Order> findAllByTransport_User_Id(Integer transport_user_id);

    List<Order> findAllByCargo_User_Id(Integer cargo_user_id);

    List<Order> findAllByTransport(Transport transport);

    List<Order> findAllByCargo(Cargo cargo);

    List<Order> findAllByStatusAndTransport_User_Id(OrderStatus status, Integer transport_user_id);

    List<Order> findAllByStatusAndCargo_User_Id(OrderStatus status, Integer transport_user_id);

    // поиск отправленных заказов на транспорт
    List<Order> findByTransport_User_IdAndCustomerId(Integer transport_user_id, Integer customerId);

    // поиск полученных заказов на транспорт
    List<Order> findByTransport_User_IdAndCustomerIdNot(Integer transport_user_id, Integer customerId);

    // поиск отправленных заказов на груз
    List<Order> findByCargo_User_IdAndCustomerId(Integer cargo_user_id, Integer customerId);

    // поиск полученных заказов на груз
    List<Order> findByCargo_User_IdAndCustomerIdNot(Integer cargo_user_id, Integer customerId);


}