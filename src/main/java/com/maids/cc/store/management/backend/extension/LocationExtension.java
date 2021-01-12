package com.maids.cc.store.management.backend.extension;

import com.maids.cc.store.management.backend.dto.request.LocationRequestDto;
import com.maids.cc.store.management.backend.dto.response.LocationResponseDto;
import com.maids.cc.store.management.backend.entity.Location;

public class LocationExtension {

    public static Location toLocationEntity(LocationRequestDto locationRequestDto){

        Location location = new Location();

        location.setCity(locationRequestDto.city);
        location.setZone(locationRequestDto.zone);
        location.setStreet(locationRequestDto.street);

        return location;
    }

    public static LocationResponseDto toLocationDto(Location location){

        LocationResponseDto locationResponseDto = new LocationResponseDto();

        locationResponseDto.id = location.getId();
        locationResponseDto.city = location.getCity();
        locationResponseDto.zone = location.getZone();
        locationResponseDto.street = location.getStreet();

        return locationResponseDto;
    }
}
