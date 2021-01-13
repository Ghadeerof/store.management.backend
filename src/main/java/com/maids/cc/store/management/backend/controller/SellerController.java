package com.maids.cc.store.management.backend.controller;

import com.maids.cc.store.management.backend.dto.request.SellerRequestDto;
import com.maids.cc.store.management.backend.dto.response.SellerResponseDto;
import com.maids.cc.store.management.backend.service.SellerService;
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
@RequestMapping("/seller")
@Api(value = "Seller Management end-points", description = "Main CRUD end-points into Seller")
public class SellerController {

    //region Properties
    @Autowired
    SellerService sellerService;
    //endregion

    //region Get By Id
    @ApiOperation(value = "retrieve specific seller from the database", response = SellerResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the seller item"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "{Id}")
    public ResponseEntity<SellerResponseDto> get(@PathVariable UUID Id) {

        ResponseEntity<SellerResponseDto> result = sellerService.getSeller(Id);

        return result;
    }
    //endregion

    //region Get All
    @ApiOperation(value = "retrieve all the seller from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved seller list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<List<SellerResponseDto>> getAll() {

        ResponseEntity<List<SellerResponseDto>> result = sellerService.getAllSellers();

        return result;
    }

    @ApiOperation(value = "retrieve all the sellers by specific street from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sellers list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "get-seller-by-street/{street}")
    public ResponseEntity<List<SellerResponseDto>> getSellersByStreet(@PathVariable String street) {

        ResponseEntity<List<SellerResponseDto>> result = sellerService.getSellersByStreet(street);

        return result;
    }
    //endregion

    //region Post
    @ApiOperation(value = "add a new seller to the database", response = SellerResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "seller has been added successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    public ResponseEntity<SellerResponseDto> addSeller(@RequestBody SellerRequestDto dto) {

        ResponseEntity<SellerResponseDto> result = sellerService.addSeller(dto);

        return result;
    }
    //endregion

    //region Delete
    @ApiOperation(value = "Delete specific seller from the database", response = boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "seller has been removed successfully!"),
            @ApiResponse(code = 204, message = "seller already had been removed from the database!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "{Id}")
    public boolean deleteSeller(@PathVariable UUID Id) {

        boolean result = sellerService.deleteSeller(Id);

        return result;
    }
    //endregion
}
