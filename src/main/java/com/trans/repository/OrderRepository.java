package com.trans.repository;

import com.trans.model.Cargo;
import com.trans.model.Order;
import com.trans.model.Transport;
import com.trans.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findAllByTransport_User_Id(Integer transport_user_id,
                                           Pageable pageable);

    Page<Order> findAllByCargo_User_Id(Integer cargo_user_id,
                                           Pageable pageable);

    List<Order> findAllByCargoId(Integer cargo_id);

    List<Order> findAllByCargo_User_Id(Integer cargo_user_id);

    @Query("select o from Order o where o.transport.isDelete = ?1")
    List<Order> findAllByTransport_Delete(boolean isDelete);
    @Query("select o from Order o where o.cargo.isDelete = ?1")
    List<Order> findAllByCargo_Delete(boolean isDelete);


    // поиск отправленных заказов на транспорт

    @Query("select o from Order o where o.transport.user.id = ?1 and o.customerId = ?2 and o.transport.isDelete = ?3")
    Page<Order> findByTransport_User_IdAndCustomerIdAndTransport_Delete(Integer transport_user_id, Integer customerId, boolean isDelete,
                                                                        Pageable pageable);

    // поиск полученных заказов на транспорт
    @Query("select o from Order o where o.transport.user.id = ?1 and o.customerId <> ?2 and o.transport.isDelete = ?3")
    Page<Order> findByTransport_User_IdAndCustomerIdNotAndTransport_Delete(Integer transport_user_id, Integer customerId, boolean isDelete,
                                                                           Pageable pageable);

    // поиск отправленных заказов на груз
    @Query("select o from Order o where o.cargo.user.id = ?1 and o.customerId = ?2 and o.cargo.isDelete = ?3")
    Page<Order> findByCargo_User_IdAndCustomerId_AndCargo_Delete(Integer cargo_user_id, Integer customerId, boolean isDelete,
                                                                 Pageable pageable);

    // поиск полученных заказов на груз
    @Query("select o from Order o where o.cargo.user.id = ?1 and o.customerId <> ?2 and o.cargo.isDelete = ?3")
    Page<Order> findByCargo_User_IdAndCustomerIdNotAndCargo_Delete(Integer cargo_user_id, Integer customerId, boolean isDelete,
                                                                   Pageable pageable);

}