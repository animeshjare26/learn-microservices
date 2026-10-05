package com.animesh.ecommerce.inventory_service.services;

import com.animesh.ecommerce.inventory_service.dto.OrderRequestDto;
import com.animesh.ecommerce.inventory_service.dto.ProductDto;
import com.animesh.ecommerce.inventory_service.dto.ProductRequestDto;

import java.util.List;

public interface ProductService {

    public List<ProductDto> getAll();

    public ProductDto getById(Long id);

    void create(ProductRequestDto dto);

    Double reduceStocks(OrderRequestDto orderRequestDto);
}
