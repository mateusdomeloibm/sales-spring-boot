package com.ibm.mateusmelo.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemInfoDTO {
    private String productName;
    private String sku;
    private BigDecimal price;
    private Integer quantity;
}
