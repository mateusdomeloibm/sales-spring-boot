package com.ibm.mateusmelo.domain.repository;

import com.ibm.mateusmelo.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItems extends JpaRepository<OrderItem, Integer> {
}
