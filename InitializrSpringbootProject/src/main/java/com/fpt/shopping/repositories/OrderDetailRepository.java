package com.fpt.shopping.repositories;

import com.fpt.shopping.entities.OrderDetail;
import com.fpt.shopping.entities.OrderDetailIdentity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailIdentity> {

    @Query("SELECT od FROM OrderDetail od WHERE od.orderDetailIdentify.order.id = :orderId")
    List<OrderDetail> search(@Param("orderId") int orderId);
}
