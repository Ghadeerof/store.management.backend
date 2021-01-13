package com.maids.cc.store.management.backend.service;

import com.maids.cc.store.management.backend.dto.request.SaleRequestDto;
import com.maids.cc.store.management.backend.dto.response.SaleResponseDto;
import com.maids.cc.store.management.backend.entity.Client;
import com.maids.cc.store.management.backend.entity.Seller;
import com.maids.cc.store.management.backend.entity.Sale;
import com.maids.cc.store.management.backend.extension.SaleExtension;
import com.maids.cc.store.management.backend.repository.ClientRepository;
import com.maids.cc.store.management.backend.repository.SaleRepository;
import com.maids.cc.store.management.backend.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SaleService {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    SellerRepository sellerRepository;

    public ResponseEntity<SaleResponseDto> getSale(UUID id){

        try {
            Sale sale = saleRepository.get(id);

            if(sale == null){
                return new ResponseEntity<>(null, HttpStatus.FAILED_DEPENDENCY);
            }

            SaleResponseDto dto = SaleExtension.toSaleDto(sale);

            return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<SaleResponseDto>> getAllSales(){
        try {
            List<Sale> sales = saleRepository.getAll();

            List<SaleResponseDto> saleDtos = sales.stream()
                    .map(SaleExtension::toSaleDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(saleDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<SaleResponseDto>> getSalesOfClient(UUID clientId){
        try {
            List<Sale> sales = saleRepository.getAllSalesOfClient(clientId);

            List<SaleResponseDto> saleDtos = sales.stream()
                    .map(SaleExtension::toSaleDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(saleDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<SaleResponseDto>> getSalesOfSeller(UUID sellerId){
        try {
            List<Sale> sales = saleRepository.getAllSalesOfSeller(sellerId);

            List<SaleResponseDto> saleDtos = sales.stream()
                    .map(SaleExtension::toSaleDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(saleDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<SaleResponseDto> addSale(SaleRequestDto dto){
        try {
            Sale sale = SaleExtension.toSaleEntity(dto);
            Sale addedSale = saleRepository.save(sale);

            if(dto.clientId != null){
                Client client = clientRepository.get(dto.clientId);

                addedSale.setClient(client);

                client.addSale(addedSale);

                clientRepository.save(client);

                addedSale = saleRepository.save(addedSale);
            }

            if(dto.sellerId != null){
                Seller seller = sellerRepository.get(dto.sellerId);

                addedSale.setSeller(seller);

                seller.addSale(sale);

                sellerRepository.save(seller);

                addedSale = saleRepository.save(addedSale);
            }

            SaleResponseDto responseDto = SaleExtension.toSaleDto(addedSale);

            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public boolean deleteSale(UUID id){
        Sale sale = saleRepository.get(id);

        if(sale == null){
            return false;
        }

        saleRepository.delete(id);

        return true;
    }
}
