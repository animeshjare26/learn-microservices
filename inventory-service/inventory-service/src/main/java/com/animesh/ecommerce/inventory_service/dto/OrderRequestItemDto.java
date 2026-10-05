package com.animesh.ecommerce.inventory_service.dto;

import lombok.Data;

@Data
public class OrderRequestItemDto {

    Long productId;

    Long quantity;

}
