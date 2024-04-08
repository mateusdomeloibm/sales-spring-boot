package com.ibm.mateusmelo.rest.controller;

import com.ibm.mateusmelo.domain.entity.Order;
import com.ibm.mateusmelo.domain.entity.OrderItem;
import com.ibm.mateusmelo.domain.enums.OrderStatus;
import com.ibm.mateusmelo.exception.OrderNotFoundException;
import com.ibm.mateusmelo.rest.dto.OrderDTO;
import com.ibm.mateusmelo.rest.dto.OrderInfoDTO;
import com.ibm.mateusmelo.rest.dto.OrderItemInfoDTO;
import com.ibm.mateusmelo.rest.dto.UpdateOrderStatusDTO;
import com.ibm.mateusmelo.service.OrderService;
import static org.springframework.http.HttpStatus.*;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public boolean save(@Valid @RequestBody OrderDTO dto) {
        return service.save(dto) != null;
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable("id") Integer id, @Valid @RequestBody UpdateOrderStatusDTO statusDto) {
        OrderStatus status = OrderStatus.valueOf(statusDto.getStatus());
        service.update(id, status);
    }

    @GetMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public OrderInfoDTO getOrderDetailsById(@PathVariable("id") Integer id) {
        return service.getOrderById(id)
                .map(this::converter)
                .orElseThrow(
                        () -> new OrderNotFoundException(String.format("Order (with ID %d) not found", id))
                );
    }

    private OrderInfoDTO converter(Order order) {
        return OrderInfoDTO.builder()
                .id(order.getId())
                .clientName(order.getClient().getName())
                .total(order.getTotal())
                .items(converter(order.getOrderItems()))
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
    }

    private Set<OrderItemInfoDTO> converter(Set<OrderItem> orderItems) {
        if(orderItems.isEmpty()) {
            return Collections.emptySet();
        }

        return orderItems.stream().map(
                item -> OrderItemInfoDTO.builder()
                        .productName(item.getProduct().getName())
                        .sku(item.getSku())
                        .quantity(item.getQuantity())
                        .price(item.getProduct().getPrice())
                        .build()
        ).collect(Collectors.toSet());
    }
}
