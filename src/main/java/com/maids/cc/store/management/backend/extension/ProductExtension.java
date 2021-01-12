package com.maids.cc.store.management.backend.extension;

import com.maids.cc.store.management.backend.dto.request.ProductRequestDto;
import com.maids.cc.store.management.backend.dto.response.ProductResponseDto;
import com.maids.cc.store.management.backend.entity.Product;

public class ProductExtension {

    public static Product toProductEntity(ProductRequestDto productRequestDto){

        Product product = new Product();

        product.setName(productRequestDto.name);
        product.setDescription(productRequestDto.description);
        product.setPrice(productRequestDto.price);
        product.setQuantity(productRequestDto.quantity);

        return product;
    }

    public static ProductResponseDto toProductDto(Product product){

        ProductResponseDto productResponseDto = new ProductResponseDto();

        productResponseDto.id = product.getId();
        productResponseDto.name = product.getName();
        productResponseDto.description = product.getDescription();
        productResponseDto.price = product.getPrice();
        productResponseDto.quantity = product.getQuantity();

        productResponseDto.categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
        productResponseDto.categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
        productResponseDto.categoryDescription = product.getCategory() != null ? product.getCategory().getDescription() : null;

        return productResponseDto;
    }
}
