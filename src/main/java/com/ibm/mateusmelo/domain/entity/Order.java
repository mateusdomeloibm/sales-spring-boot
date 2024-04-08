package com.ibm.mateusmelo.domain.entity;

import com.ibm.mateusmelo.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems;

    @Enumerated
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "total", scale = 2, precision = 20)
    private BigDecimal total;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
