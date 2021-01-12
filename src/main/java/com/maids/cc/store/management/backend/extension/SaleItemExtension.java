package com.maids.cc.store.management.backend.extension;

import com.maids.cc.store.management.backend.dto.request.SaleItemRequestDto;
import com.maids.cc.store.management.backend.dto.response.SaleItemResponseDto;
import com.maids.cc.store.management.backend.entity.Sale;
import com.maids.cc.store.management.backend.entity.SaleItem;

public class SaleItemExtension {

    public static SaleItem toSaleItemEntity(SaleItemRequestDto saleItemRequestDto){

        SaleItem saleItem = new SaleItem();

        saleItem.setQuantity(saleItemRequestDto.quantity);
        saleItem.setPrice(saleItemRequestDto.price);

        return saleItem;
    }

    public static SaleItemResponseDto toSaleItemDto(SaleItem saleItem){

        SaleItemResponseDto saleItemResponseDto = new SaleItemResponseDto();

        saleItemResponseDto.id = saleItem.getId();
        saleItemResponseDto.quantity = saleItem.getQuantity();
        saleItemResponseDto.price = saleItem.getPrice();

        saleItemResponseDto.productId = saleItem.getProduct() != null ? saleItem.getProduct().getId() : null;
        saleItemResponseDto.saleId = saleItem.getSale() != null ? saleItem.getSale().getId() : null;

        return saleItemResponseDto;
    }
}
