package com.animesh.ecommerce.order_service.dtos;

import com.animesh.ecommerce.order_service.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    private OrderStatus orderStatus;


    private List<OrderItemRequestDto> orderItems;


    private Double totalPrice;
}
