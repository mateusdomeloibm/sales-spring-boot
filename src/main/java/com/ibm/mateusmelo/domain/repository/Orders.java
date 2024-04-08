package com.ibm.mateusmelo.domain.repository;

import com.ibm.mateusmelo.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface Orders extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.id = :id ")
    Optional<Order> findByIdFetchOrderItems(Integer id);
}
