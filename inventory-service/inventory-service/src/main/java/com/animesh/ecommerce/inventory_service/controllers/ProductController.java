package com.animesh.ecommerce.inventory_service.controllers;

import com.animesh.ecommerce.inventory_service.dto.OrderRequestDto;
import com.animesh.ecommerce.inventory_service.dto.ProductDto;
import com.animesh.ecommerce.inventory_service.dto.ProductRequestDto;
import com.animesh.ecommerce.inventory_service.repository.ProductRepository;
import com.animesh.ecommerce.inventory_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping("/fetchOrders")
    public String fetchFromOrderService(){

        ServiceInstance orderInstance = discoveryClient.getInstances("order-service").getFirst();

        return  restClient.get()
                .uri(orderInstance.getUri() + "/api/orders/core/hello")
                .retrieve()
                .body(String.class);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProductRequestDto dto){
        productService.create(dto);
        return ResponseEntity.ok("Product created Successfully");
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAl(){
        List<ProductDto> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable("id") Long id ){
        ProductDto product = productService.getById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto){
        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return  ResponseEntity.ok(totalPrice);
    }
}
