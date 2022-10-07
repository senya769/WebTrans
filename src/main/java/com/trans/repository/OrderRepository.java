package com.trans.repository;

import com.trans.model.Order;
import com.trans.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    //List<Order> findAllByTransportUserId(int transport_user_id);
    List<Order> findAllByTransport_User_Id(int transport_user_id);
    List<Order> findAllByTransport(Transport transport);
}
