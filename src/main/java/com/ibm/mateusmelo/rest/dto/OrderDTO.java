package com.ibm.mateusmelo.rest.dto;

import java.lang.Integer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.mateusmelo.validation.NotEmptyList;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @JsonProperty(value = "client_id")
    @NotNull(message = "{field.client.required}")
    private Integer client;

    @NotEmptyList(message = "{field.order-item-list.required}")
    private Set<OrderItemDTO> items;

    @NotNull(message = "{field.total.required}")
    private BigDecimal total;

    private LocalDate createdAt;
}
