package com.maids.cc.store.management.backend.controller;

import com.maids.cc.store.management.backend.dto.request.LocationRequestDto;
import com.maids.cc.store.management.backend.dto.response.LocationResponseDto;
import com.maids.cc.store.management.backend.service.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController    // This means that this class is a Controller
@RequestMapping("/location")
@Api(value = "Location Management end-points", description = "Main CRUD end-points into Location")
public class LocationController {

    //region Properties
    @Autowired
    LocationService locationService;
    //endregion

    //region Get By Id
    @ApiOperation(value = "retrieve specific location from the database", response = LocationResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the location"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "{Id}")
    public ResponseEntity<LocationResponseDto> get(@PathVariable UUID Id) {

        ResponseEntity<LocationResponseDto> result = locationService.getLocation(Id);

        return result;
    }
    //endregion

    //region Get All
    @ApiOperation(value = "retrieve all the locations from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved locations list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<List<LocationResponseDto>> getAll() {

        ResponseEntity<List<LocationResponseDto>> result = locationService.getAllLocations();

        return result;
    }
    //endregion

    //region Post
    @ApiOperation(value = "add a new location to the database", response = LocationResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "location has been added successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    public ResponseEntity<LocationResponseDto> addLocation(@RequestBody LocationRequestDto dto) {

        ResponseEntity<LocationResponseDto> result = locationService.addLocation(dto);

        return result;
    }
    //endregion

    //region Put
    @ApiOperation(value = "update a location to the database", response = LocationResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "location has been updated successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "{Id}")
    public ResponseEntity<LocationResponseDto> updateLocation(@PathVariable UUID Id, @RequestBody LocationRequestDto dto) {

        ResponseEntity<LocationResponseDto> result = locationService.updateLocation(Id, dto);

        return result;
    }
    //endregion

    //region Delete
    @ApiOperation(value = "Delete specific location from the database", response = boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "location has been removed successfully!"),
            @ApiResponse(code = 204, message = "location already had been removed from the database!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "{Id}")
    public boolean deleteLocation(@PathVariable UUID Id) {

        boolean result = locationService.deleteLocation(Id);

        return result;
    }
    //endregion
}
