package com.fpt.shopping.repositories;

import com.fpt.shopping.entities.Order;
import com.fpt.shopping.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT od FROM Order od WHERE od.userId = :userId")
    List<Order> getByName(@Param("userId") int userId);
}
