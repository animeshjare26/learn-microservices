package com.animesh.ecommerce.order_service.clients;

import com.animesh.ecommerce.order_service.dtos.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "INVENTORY-SERVICE", path = "/api/inventory")
public interface InventoryOpenFeignClient {

    @PutMapping("/product/reduce-stocks")
    Double reduceStocks(@RequestBody OrderRequestDto orderRequestDto);
}
