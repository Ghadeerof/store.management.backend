package com.maids.cc.store.management.backend.extension;

import com.maids.cc.store.management.backend.dto.request.SaleRequestDto;
import com.maids.cc.store.management.backend.dto.response.SaleResponseDto;
import com.maids.cc.store.management.backend.entity.Sale;

import java.util.stream.Collectors;

public class SaleExtension {

    public static Sale toSaleEntity(SaleRequestDto saleRequestDto){

        return new Sale();
    }

    public static SaleResponseDto toSaleDto(Sale sale){

        SaleResponseDto saleResponseDto = new SaleResponseDto();

        saleResponseDto.id = sale.getId();

        saleResponseDto.clientId = sale.getClient() != null ? sale.getClient().getId() : null;
        saleResponseDto.clientFullName = sale.getClient() != null ?
                sale.getClient().getFirstName() + " " + sale.getClient().getLastName() : null;

        saleResponseDto.sellerId = sale.getSeller() != null ? sale.getSeller().getId() : null;
        saleResponseDto.sellerFullName = sale.getSeller() != null ?
                sale.getSeller().getFirstName() + " " + sale.getSeller().getLastName() : null;

        if(sale.getSaleItemSet() != null && ! sale.getSaleItemSet().isEmpty()){
            saleResponseDto.saleItemResponseDtoSet = sale.getSaleItemSet().stream()
                    .map(SaleItemExtension::toSaleItemDto)
                    .collect(Collectors.toList());
        }
        return saleResponseDto;
    }
}
