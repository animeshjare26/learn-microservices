package com.animesh.ecommerce.inventory_service.clients;

import com.animesh.ecommerce.inventory_service.dto.OrderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "order-service",path = "/api/orders")
public interface OrderFeignClient {

    @GetMapping("/core")
    public List<OrderResponseDto> getAllOrders();
}
