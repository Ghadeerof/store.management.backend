package com.maids.cc.store.management.backend.service;

import com.maids.cc.store.management.backend.dto.request.SellerRequestDto;
import com.maids.cc.store.management.backend.dto.response.SellerResponseDto;
import com.maids.cc.store.management.backend.entity.Seller;
import com.maids.cc.store.management.backend.entity.Location;
import com.maids.cc.store.management.backend.extension.SellerExtension;
import com.maids.cc.store.management.backend.repository.SellerRepository;
import com.maids.cc.store.management.backend.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Component
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    LocationRepository locationRepository;

    public ResponseEntity<SellerResponseDto> getSeller(UUID id){

        try {
            Seller seller = sellerRepository.get(id);

            if(seller == null){
                return new ResponseEntity<>(null, HttpStatus.FAILED_DEPENDENCY);
            }

            SellerResponseDto dto = SellerExtension.toSellerDto(seller);

            return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<SellerResponseDto>> getAllSellers(){
        try {
            List<Seller> sellers = sellerRepository.getAll();

            List<SellerResponseDto> sellerDtos = sellers.stream()
                    .map(SellerExtension::toSellerDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(sellerDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<SellerResponseDto>> getSellersByStreet(String street){
        try {
            List<Seller> sellers = sellerRepository.getAllSellersByStreet(street);

            List<SellerResponseDto> sellerDtos = sellers.stream()
                    .map(SellerExtension::toSellerDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(sellerDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<SellerResponseDto> addSeller(SellerRequestDto dto){
        try {
            Seller seller = SellerExtension.toSellerEntity(dto);
            Seller addedSeller = sellerRepository.save(seller);

            if(dto.locationId != null){
                Location location = locationRepository.get(dto.locationId);

                addedSeller.setLocation(location);

                location.addSeller(addedSeller);

                sellerRepository.save(seller);

                addedSeller = sellerRepository.save(addedSeller);
            }

            SellerResponseDto responseDto = SellerExtension.toSellerDto(addedSeller);

            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<SellerResponseDto> updateSeller(UUID id, SellerRequestDto dto){

        try {
            Seller seller = sellerRepository.get(id);

            if(seller == null){
                return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
            }

            if(dto.locationId != null){
                Location location = locationRepository.get(dto.locationId);

                if(location != null){

                    //remove not updated seller
                    location.removeSeller(seller);

                    seller.setLocation(location);

                    //add updated seller
                    location.addSeller(seller);

                    locationRepository.save(location);
                }
            }

            Seller addedSeller = sellerRepository.save(seller);

            SellerResponseDto responseDto = SellerExtension.toSellerDto(addedSeller);

            return new ResponseEntity<>(responseDto,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public boolean deleteSeller(UUID id){
        Seller seller = sellerRepository.get(id);

        if(seller == null){
            return false;
        }

        sellerRepository.delete(id);

        return true;
    }
}
