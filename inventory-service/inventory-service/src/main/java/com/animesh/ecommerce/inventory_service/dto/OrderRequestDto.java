package com.animesh.ecommerce.inventory_service.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {


    @JsonProperty("orderItems")
    @JsonAlias({"orderRequestItems"})
    private List<OrderRequestItemDto> orderItems;
}
