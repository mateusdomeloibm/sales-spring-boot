package com.ibm.mateusmelo.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "{field.name.required}")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "sku")
    @NotEmpty(message = "{field.sku.required}")
    private String sku;

    @Column(name = "price", length = 2, precision = 20)
    @NotNull(message = "{field.price.required}")
    private BigDecimal price;
}
