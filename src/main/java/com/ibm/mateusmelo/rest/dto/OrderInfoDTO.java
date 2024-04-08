package com.ibm.mateusmelo.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDTO {
    private Integer id;

    @JsonProperty("client_name")
    private String clientName;

    private Set<OrderItemInfoDTO> items;

    private String status;

    private BigDecimal total;

    @JsonProperty("created_at")
    private String createdAt;
}
