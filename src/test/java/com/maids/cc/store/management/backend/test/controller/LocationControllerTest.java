package com.maids.cc.store.management.backend.test.controller;

import com.maids.cc.store.management.backend.entity.Location;
import com.maids.cc.store.management.backend.service.LocationService;
import com.maids.cc.store.management.backend.controller.LocationController;
import com.maids.cc.store.management.backend.dto.request.LocationRequestDto;
import com.maids.cc.store.management.backend.dto.response.LocationResponseDto;

import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LocationControllerTest {

    //region Properties
    @InjectMocks
    private LocationController locationController;

    @Mock
    private LocationService locationService;

    private Location actualLocation;

    private Integer listSize;
    //endregion

    //region Initialization
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        seedRepo();
    }

    private void seedRepo(){

        //region used values
        listSize = 4;

        UUID id = UUID.randomUUID();
        String city = "Location city";
        String zone = "Location zone";
        String street = "Location street";
        //endregion

        //region entity
        actualLocation = new Location();
        actualLocation.setId(id);
        actualLocation.setCity(city);
        actualLocation.setZone(zone);
        actualLocation.setStreet(street);
        //endregion

        //region responseDto
        LocationResponseDto LocationResponseDto = new LocationResponseDto();
        LocationResponseDto.id = id;
        LocationResponseDto.city = city;
        LocationResponseDto.zone = zone;
        LocationResponseDto.street = street;

        ResponseEntity<LocationResponseDto> responseEntity = new ResponseEntity<>(LocationResponseDto, HttpStatus.ACCEPTED);
        //endregion

        //region list of entity
        List<LocationResponseDto> locationsList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            locationsList.add(LocationResponseDto);

        ResponseEntity<List<LocationResponseDto>> responseEntityList =
                new ResponseEntity<>(locationsList,HttpStatus.ACCEPTED);
        //endregion

        //region mock actions

        //region add Location case
        when(locationService.addLocation(any(LocationRequestDto.class))).thenReturn(responseEntity);
        //endregion

        //region update Location case
        when(locationService.updateLocation(any(UUID.class),any(LocationRequestDto.class))).thenReturn(responseEntity);
        //endregion

        //region get Location case
        when(locationService.getLocation(id)).thenReturn(responseEntity);
        //endregion

        //region delete Location case
        when(locationService.deleteLocation(id)).thenReturn(true);
        //endregion

        //region get all Locations case
        when(locationService.getAllLocations()).thenReturn(responseEntityList);
        //endregion

        //endregion
    }
    //endregion

    //region Tests
    @Test
    public void testLocationAdd(){
        LocationRequestDto locationRequestDto = new LocationRequestDto();
        locationRequestDto.city = actualLocation.getCity();
        locationRequestDto.zone = actualLocation.getZone();
        locationRequestDto.street = actualLocation.getStreet();

        LocationResponseDto addedLocation = locationController.addLocation(locationRequestDto).getBody();

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

        LocationResponseDto updatedLocation = locationController.updateLocation(actualLocation.getId(), locationRequestDto).getBody();

        assertNotNull(updatedLocation);
        assertNotNull(updatedLocation.id);
        assertEquals(updatedLocation.city, locationRequestDto.city);
        assertEquals(updatedLocation.zone, locationRequestDto.zone);
        assertEquals(updatedLocation.street, locationRequestDto.street);
    }

    @Test
    public void testLocationGet(){
        LocationResponseDto expectedLocation = locationController.get(actualLocation.getId()).getBody();

        assertNotNull(expectedLocation);
        assertEquals(expectedLocation.id, actualLocation.getId());
        assertEquals(expectedLocation.city, actualLocation.getCity());
        assertEquals(expectedLocation.zone, actualLocation.getZone());
        assertEquals(expectedLocation.street, actualLocation.getStreet());
    }

    @Test
    public void testLocationGetAll(){
        List<LocationResponseDto> list = locationController.getAll().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(locationService, times(1)).getAllLocations();
    }

    @Test
    public void testLocationDelete(){
        Boolean expectedResult = locationController.deleteLocation(actualLocation.getId());

        assertEquals(expectedResult, true);
        verify(locationService,times(1)).deleteLocation(actualLocation.getId());
    }
    //endregion
}
