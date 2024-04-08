package com.ibm.mateusmelo.service;

import com.ibm.mateusmelo.domain.entity.Order;
import com.ibm.mateusmelo.domain.enums.OrderStatus;
import com.ibm.mateusmelo.rest.dto.OrderDTO;

import java.util.Optional;

public interface OrderService {
    Order getById(Integer id);

    Order save(OrderDTO dto);

    void update(Integer id, OrderStatus status);

    Optional<Order> getOrderById(Integer id);
}
