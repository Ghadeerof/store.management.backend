package com.maids.cc.store.management.backend.dto.response;

import java.util.List;
import java.util.UUID;

public class SaleResponseDto {

    public UUID id;

    public UUID clientId;

    public String clientFullName;

    public UUID sellerId;

    public String sellerFullName;

    public float total;

    public List<SaleItemResponseDto> saleItemResponseDtoSet;
}
