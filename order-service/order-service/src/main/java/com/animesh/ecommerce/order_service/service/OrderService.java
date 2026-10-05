package com.animesh.ecommerce.order_service.service;

import com.animesh.ecommerce.order_service.dtos.OrderRequestDto;
import com.animesh.ecommerce.order_service.dtos.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto dto);

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto getOrderById(Long id);
}
