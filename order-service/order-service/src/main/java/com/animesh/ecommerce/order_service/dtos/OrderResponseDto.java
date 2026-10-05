package com.animesh.ecommerce.order_service.dtos;

import com.animesh.ecommerce.order_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;

    private OrderStatus orderStatus;

    private Double price;

    private List<OrderItemResponseDto> orderItems;
}
