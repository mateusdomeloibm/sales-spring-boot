package com.ibm.mateusmelo.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusDTO {
    @NotEmpty(message = "Status invalid")
    private String status;
}
