package com.animesh.ecommerce.inventory_service.services.Impl;

import com.animesh.ecommerce.inventory_service.dto.OrderRequestDto;
import com.animesh.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.animesh.ecommerce.inventory_service.dto.ProductDto;
import com.animesh.ecommerce.inventory_service.dto.ProductRequestDto;
import com.animesh.ecommerce.inventory_service.entity.Product;
import com.animesh.ecommerce.inventory_service.repository.ProductRepository;
import com.animesh.ecommerce.inventory_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductDto> getAll() {
        log.info("Fetching all inventory items");
        List<Product> inventories = productRepository.findAll();
        return inventories.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();
    }

    @Override
    public ProductDto getById(Long id) {
        log.info("fetched inventory by id : {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DateTimeException("Product not found :" + id));

        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public void create(ProductRequestDto dto) {

        Product product = new Product();
        modelMapper.map(dto,product);
        productRepository.save(product);
    }

    @Transactional
    @Override
    public Double reduceStocks(OrderRequestDto orderRequestDto) {

        log.info("Reducing the stocks");
        Double totalPrice = 0.0;
        if (orderRequestDto == null || orderRequestDto.getOrderItems() == null || orderRequestDto.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order items are required");
        }
        for (OrderRequestItemDto orderRequestItems : orderRequestDto.getOrderItems()){

            Long productId = orderRequestItems.getProductId();
            Long quantity = orderRequestItems.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(()-> new DateTimeException("Product not found : " + productId));

            if (product.getStock() < quantity){
                throw  new DateTimeException("Quantity cannot be fulfilled currently !");
            }
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            totalPrice += product.getPrice() * quantity;
        }
        return totalPrice;
    }


}
