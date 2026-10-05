package com.animesh.ecommerce.order_service.service.impl;

import com.animesh.ecommerce.order_service.clients.InventoryOpenFeignClient;
import com.animesh.ecommerce.order_service.dtos.OrderItemRequestDto;
import com.animesh.ecommerce.order_service.dtos.OrderItemResponseDto;
import com.animesh.ecommerce.order_service.dtos.OrderRequestDto;
import com.animesh.ecommerce.order_service.dtos.OrderResponseDto;
import com.animesh.ecommerce.order_service.entity.OrderItem;
import com.animesh.ecommerce.order_service.entity.Orders;
import com.animesh.ecommerce.order_service.enums.OrderStatus;
import com.animesh.ecommerce.order_service.repository.OrderRepository;
import com.animesh.ecommerce.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;


    @Override
    public List<OrderResponseDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Orders> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        log.info("Fetching order by id: {}", id);
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return mapToResponseDto(order);
    }

    private OrderResponseDto mapToResponseDto(Orders order) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(order.getId());
        responseDto.setOrderStatus(order.getOrderStatus());
        responseDto.setPrice(order.getPrice());

        if (order.getOrderItems() != null) {
            List<OrderItemResponseDto> itemDtos = order.getOrderItems().stream()
                    .map(item -> modelMapper.map(item, OrderItemResponseDto.class))
                    .toList();
            responseDto.setOrderItems(itemDtos);
        }

        return responseDto;
    }


    @Override
//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker",fallbackMethod = "createOrderFallback")
    @RateLimiter(name = "inventoryRateLimiter",fallbackMethod = "createOrderFallback")
    public OrderResponseDto createOrder(OrderRequestDto dto) {

        Double totalPrice = inventoryOpenFeignClient.reduceStocks(dto);
        Orders orders = new Orders();
        for (OrderItemRequestDto itemDto : dto.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(itemDto.getProductId());
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setOrder(orders);
            orders.getOrderItems().add(orderItem);
        }
        orders.setPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(orders);

        return modelMapper.map(orders, OrderResponseDto.class);
    }

    public OrderResponseDto createOrderFallback(OrderRequestDto dto, Throwable throwable) {

        log.info("Fallback occurred due to : {}", throwable.getMessage());

        return new OrderResponseDto();
    }
}
