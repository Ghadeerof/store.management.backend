package com.maids.cc.store.management.backend.test.service;

import com.maids.cc.store.management.backend.dto.request.LocationRequestDto;
import com.maids.cc.store.management.backend.dto.response.LocationResponseDto;
import com.maids.cc.store.management.backend.entity.Location;
import com.maids.cc.store.management.backend.repository.LocationRepository;
import com.maids.cc.store.management.backend.service.LocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTest {

    //region Properties
    @InjectMocks
    private LocationService locationService;

    @Mock
    private LocationRepository locationRepository;

    private Location actualLocation;

    private Integer listSize;
    //endregion

    //region Initialization
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        seedData();
    }

    private void seedData(){
        //region used values
        listSize = 5;

        UUID id = UUID.randomUUID();
        String city = "location city";
        String zone = "location zone";
        String street = "location street";
        //endregion

        //region entity
        actualLocation = new Location();
        actualLocation.setId(id);
        actualLocation.setCity(city);
        actualLocation.setZone(zone);
        actualLocation.setStreet(street);
        //endregion

        //region list of entity
        List<Location> locationsList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            locationsList.add(actualLocation);
        //endregion

        //region mock actions

        //region add location case
        when(locationRepository.save(any(Location.class))).thenReturn(actualLocation);
        //endregion

        //region get location case
        when(locationRepository.get(id)).thenReturn(actualLocation);
        //endregion

        //region delete location case
        doNothing().when(locationRepository).delete(id);
        //endregion

        //region get all locations case
        when(locationRepository.getAll()).thenReturn(locationsList);
        //endregion

        //endregion
    }

    //region Tests
    @Test
    public void testLocationAdd(){
        LocationRequestDto locationRequestDto = new LocationRequestDto();
        locationRequestDto.city = actualLocation.getCity();
        locationRequestDto.zone = actualLocation.getZone();
        locationRequestDto.street = actualLocation.getStreet();

        LocationResponseDto addedLocation = locationService.addLocation(locationRequestDto).getBody();

        assertNotNull(addedLocation);
        assertNotNull(addedLocation.id);
        assertEquals(addedLocation.city, locationRequestDto.city);
        assertEquals(addedLocation.zone, locationRequestDto.zone);
        assertEquals(addedLocation.street, locationRequestDto.street);
    }

    @Test
    public void testLocationUpdate(){
        LocationRequestDto locationRequestDto = new LocationRequestDto();
        locationRequestDto.city = actualLocation.getCity();
        locationRequestDto.zone = actualLocation.getZone();
        locationRequestDto.street = actualLocation.getStreet();

        LocationResponseDto updatedLocation = locationService.updateLocation(actualLocation.getId(), locationRequestDto).getBody();

        assertNotNull(updatedLocation);
        assertNotNull(updatedLocation.id);
        assertEquals(updatedLocation.city, locationRequestDto.city);
        assertEquals(updatedLocation.zone, locationRequestDto.zone);
        assertEquals(updatedLocation.street, locationRequestDto.street);
    }

    @Test
    public void testLocationGet(){
        LocationResponseDto expectedLocation = locationService.getLocation(actualLocation.getId()).getBody();

        assertNotNull(expectedLocation);
        assertEquals(expectedLocation.id, actualLocation.getId());
        assertEquals(expectedLocation.city, actualLocation.getCity());
        assertEquals(expectedLocation.zone, actualLocation.getZone());
        assertEquals(expectedLocation.street, actualLocation.getStreet());
    }

    @Test
    public void testLocationGetAll(){
        List<LocationResponseDto> list = locationService.getAllLocations().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(locationRepository, times(1)).getAll();
    }

    @Test
    public void testLocationDelete(){
        locationService.deleteLocation(actualLocation.getId());

        verify(locationRepository,times(1)).delete(actualLocation.getId());
    }
    //endregion
}
