package com.animesh.ecommerce.order_service.controllers;

import com.animesh.ecommerce.order_service.dtos.OrderRequestDto;
import com.animesh.ecommerce.order_service.dtos.OrderResponseDto;
import com.animesh.ecommerce.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/hello")
    public String getHelloOrders(@RequestHeader("X-User-Id") Long userId){
        return "Hello from Orders Service : userId" + userId;
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto dto) {
        OrderResponseDto createdOrder = orderService.createOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable("id") Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
