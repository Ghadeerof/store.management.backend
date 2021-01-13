package com.maids.cc.store.management.backend.service;

import com.maids.cc.store.management.backend.dto.request.LocationRequestDto;
import com.maids.cc.store.management.backend.dto.response.LocationResponseDto;
import com.maids.cc.store.management.backend.entity.Location;
import com.maids.cc.store.management.backend.extension.LocationExtension;
import com.maids.cc.store.management.backend.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public ResponseEntity<LocationResponseDto> getLocation(UUID id){

        try {
            Location location = locationRepository.get(id);

            if(location == null){
                return new ResponseEntity<>(null, HttpStatus.FAILED_DEPENDENCY);
            }

            LocationResponseDto dto = LocationExtension.toLocationDto(location);

            return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<LocationResponseDto>> getAllLocations(){
        try {
            List<Location> locations = locationRepository.getAll();

            List<LocationResponseDto> locationDtos = locations.stream()
                    .map(LocationExtension::toLocationDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(locationDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<LocationResponseDto> addLocation(LocationRequestDto dto){
        try {
            Location location = LocationExtension.toLocationEntity(dto);
            Location addedLocation = locationRepository.save(location);

            LocationResponseDto responseDto = LocationExtension.toLocationDto(addedLocation);

            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<LocationResponseDto> updateLocation(UUID id, LocationRequestDto dto){

        try {
            Location location = locationRepository.get(id);

            if(location == null){
                return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
            }

            location.setCity(dto.city);
            location.setZone(dto.zone);
            location.setStreet(dto.street);

            Location addedLocation = locationRepository.save(location);

            LocationResponseDto responseDto = LocationExtension.toLocationDto(addedLocation);

            return new ResponseEntity<>(responseDto,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public boolean deleteLocation(UUID id){
        Location location = locationRepository.get(id);

        if(location == null){
            return false;
        }

        locationRepository.delete(id);

        return true;
    }
}
