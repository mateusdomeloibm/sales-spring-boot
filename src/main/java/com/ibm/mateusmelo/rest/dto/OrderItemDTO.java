package com.ibm.mateusmelo.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private String sku;

    @JsonProperty(value = "product_id")
    @NotEmpty(message = "{field.product-id.required}")
    private Integer product;

    @NotEmpty(message = "{field.quantity.required}")
    private Integer quantity;
}
