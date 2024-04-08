package com.ibm.mateusmelo.service.impl;

import com.ibm.mateusmelo.domain.entity.*;
import com.ibm.mateusmelo.domain.enums.OrderStatus;
import com.ibm.mateusmelo.domain.repository.*;
import com.ibm.mateusmelo.exception.NotFoundException;
import com.ibm.mateusmelo.exception.OrderNotFoundException;
import com.ibm.mateusmelo.rest.dto.*;
import com.ibm.mateusmelo.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Orders orderRepository;
    private final Clients clientRepository;
    private final OrderItems orderItemsRepository;
    private final Products productRepository;

    @Override
    @Transactional
    public Order save(OrderDTO dto) {
        Client client = clientRepository.findById(dto.getClient())
                .orElseThrow(() -> new NotFoundException("Client not found"));

        Order order = new Order();
        order.setClient(client);
        order.setTotal(dto.getTotal());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDate.now());

        Set<OrderItem> orderItems = convertToOrderItems(order, dto.getItems());

        order.setOrderItems(orderItems);
        orderItemsRepository.saveAll(orderItems);
        orderRepository.save(order);

        return order;
    }

    @Override
    public void update(Integer id, OrderStatus status) {
        orderRepository.findById(id)
                .map(
                        order -> {
                            order.setStatus(status);
                            return orderRepository.save(order);
                        }
                ).orElseThrow(
                        () -> new OrderNotFoundException(
                                String.format("Status updating failed to order (id %s)", id)
                        )
                );
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findByIdFetchOrderItems(id);
    }

    private Set<OrderItem> convertToOrderItems(Order order, Set<OrderItemDTO> orderItemDTO) {
        return orderItemDTO.stream()
                .map(
                        item -> {
                            Product product = productRepository.findById(item.getProduct())
                                    .orElseThrow(() -> new NotFoundException("Product not found with id " + item.getProduct()));
                            OrderItem newItem = new OrderItem();
                            newItem.setSku(item.getSku());
                            newItem.setProduct(product);
                            newItem.setQuantity(item.getQuantity());
                            newItem.setOrder(order);
                            return newItem;
                        }
                ).collect(Collectors.toSet());
    }

    public Order getById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("A"));
    }
}
