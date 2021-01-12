package com.maids.cc.store.management.backend.extension;

import com.maids.cc.store.management.backend.dto.request.SellerRequestDto;
import com.maids.cc.store.management.backend.dto.response.SellerResponseDto;
import com.maids.cc.store.management.backend.entity.Seller;

public class SellerExtension {

    public static Seller toSellerEntity(SellerRequestDto sellerRequestDto){

        Seller seller = new Seller();

        seller.setFirstName(sellerRequestDto.firstName);
        seller.setLastName(sellerRequestDto.lastName);
        seller.setMobileNumber(sellerRequestDto.mobileNumber);

        return seller;
    }

    public static SellerResponseDto toSellerDto(Seller seller){

        SellerResponseDto sellerResponseDto = new SellerResponseDto();

        sellerResponseDto.id = seller.getId();
        sellerResponseDto.firstName = seller.getFirstName();
        sellerResponseDto.lastName = seller.getLastName();
        sellerResponseDto.mobileNumber = seller.getMobileNumber();

        sellerResponseDto.locationId = seller.getLocation() != null ? seller.getLocation().getId() : null;
        sellerResponseDto.city = seller.getLocation() != null ? seller.getLocation().getCity() : null;
        sellerResponseDto.zone = seller.getLocation() != null ? seller.getLocation().getZone() : null;
        sellerResponseDto.street = seller.getLocation() != null ? seller.getLocation().getStreet() : null;

        return sellerResponseDto;

    }
}
