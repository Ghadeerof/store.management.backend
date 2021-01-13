package com.maids.cc.store.management.backend.service;

import com.maids.cc.store.management.backend.dto.request.SaleItemRequestDto;
import com.maids.cc.store.management.backend.dto.response.SaleItemResponseDto;
import com.maids.cc.store.management.backend.entity.Product;
import com.maids.cc.store.management.backend.entity.Sale;
import com.maids.cc.store.management.backend.entity.SaleItem;
import com.maids.cc.store.management.backend.extension.SaleItemExtension;
import com.maids.cc.store.management.backend.repository.ProductRepository;
import com.maids.cc.store.management.backend.repository.SaleItemRepository;
import com.maids.cc.store.management.backend.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SaleItemService {

    @Autowired
    SaleItemRepository saleItemRepository;

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<SaleItemResponseDto> getSaleItem(UUID id){

        try {
            SaleItem saleItem = saleItemRepository.get(id);

            if(saleItem == null){
                return new ResponseEntity<>(null, HttpStatus.FAILED_DEPENDENCY);
            }

            SaleItemResponseDto dto = SaleItemExtension.toSaleItemDto(saleItem);

            return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<SaleItemResponseDto>> getAllSaleItems(){
        try {
            List<SaleItem> saleItems = saleItemRepository.getAll();

            List<SaleItemResponseDto> saleItemResponseDtos = saleItems.stream()
                    .map(SaleItemExtension::toSaleItemDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(saleItemResponseDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<SaleItemResponseDto>> getAllSaleItems(UUID saleId){
        try {
            List<SaleItem> saleItems = saleItemRepository.getAllSaleItems(saleId);

            List<SaleItemResponseDto> saleItemDtos = saleItems.stream()
                    .map(SaleItemExtension::toSaleItemDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(saleItemDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<SaleItemResponseDto>> getAllSaleItemsOfProduct(UUID productId){
        try {
            List<SaleItem> saleItems = saleItemRepository.getAllSaleItemsOfProduct(productId);

            List<SaleItemResponseDto> saleItemDtos = saleItems.stream()
                    .map(SaleItemExtension::toSaleItemDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(saleItemDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<SaleItemResponseDto> addSaleItem(SaleItemRequestDto dto){
        try {
            SaleItem saleItem = SaleItemExtension.toSaleItemEntity(dto);
            SaleItem addedSaleItem = saleItemRepository.save(saleItem);

            if(dto.productId != null){
                Product product = productRepository.get(dto.productId);

                addedSaleItem.setProduct(product);

                product.addSaleItem(addedSaleItem);

                productRepository.save(product);

                addedSaleItem = saleItemRepository.save(addedSaleItem);
            }

            if(dto.saleId != null){
                Sale sale = saleRepository.get(dto.saleId);

                addedSaleItem.setSale(sale);

                sale.addSaleItem(addedSaleItem);

                saleRepository.save(sale);

                addedSaleItem = saleItemRepository.save(addedSaleItem);
            }

            SaleItemResponseDto responseDto = SaleItemExtension.toSaleItemDto(addedSaleItem);

            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<SaleItemResponseDto> updateSaleItem(UUID id, SaleItemRequestDto dto){

        try {
            SaleItem saleItem = saleItemRepository.get(id);

            if(saleItem == null){
                return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
            }

            saleItem.setQuantity(dto.quantity);
            saleItem.setPrice(dto.price);

            SaleItem addedSaleItem = saleItemRepository.save(saleItem);

            SaleItemResponseDto responseDto = SaleItemExtension.toSaleItemDto(addedSaleItem);

            return new ResponseEntity<>(responseDto,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public boolean deleteSaleItem(UUID id){
        SaleItem saleItem = saleItemRepository.get(id);

        if(saleItem == null){
            return false;
        }

        saleItemRepository.delete(id);

        return true;
    }
}
