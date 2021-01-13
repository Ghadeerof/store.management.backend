package com.maids.cc.store.management.backend.controller;

import com.maids.cc.store.management.backend.dto.request.SaleRequestDto;
import com.maids.cc.store.management.backend.dto.response.SaleResponseDto;
import com.maids.cc.store.management.backend.service.SaleService;
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
@RequestMapping("/sale")
@Api(value = "Sale Management end-points", description = "Main CRUD end-points into Sale")
public class SaleController {

    //region Properties
    @Autowired
    SaleService saleService;
    //endregion

    //region Get By Id
    @ApiOperation(value = "retrieve specific sale from the database", response = SaleResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the sale"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "{Id}")
    public ResponseEntity<SaleResponseDto> get(@PathVariable UUID Id) {

        ResponseEntity<SaleResponseDto> result = saleService.getSale(Id);

        return result;
    }
    //endregion

    //region Get All
    @ApiOperation(value = "retrieve all the sales from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sales list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<List<SaleResponseDto>> getAll() {

        ResponseEntity<List<SaleResponseDto>> result = saleService.getAllSales();

        return result;
    }

    @ApiOperation(value = "retrieve all the sales for specific client from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sales list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "get-sales-of-client/{clientId}")
    public ResponseEntity<List<SaleResponseDto>> getAllSalesOfClient(@PathVariable UUID clientId) {

        ResponseEntity<List<SaleResponseDto>> result = saleService.getSalesOfClient(clientId);

        return result;
    }

    @ApiOperation(value = "retrieve all the sales for specific seller from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sales list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "get-sales-of-seller/{sellerId}")
    public ResponseEntity<List<SaleResponseDto>> getAllSalesOfSeller(@PathVariable UUID sellerId) {

        ResponseEntity<List<SaleResponseDto>> result = saleService.getSalesOfSeller(sellerId);

        return result;
    }
    //endregion

    //region Post
    @ApiOperation(value = "add a new sale to the database", response = SaleResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "sale has been added successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    public ResponseEntity<SaleResponseDto> addSale(@RequestBody SaleRequestDto dto) {

        ResponseEntity<SaleResponseDto> result = saleService.addSale(dto);

        return result;
    }
    //endregion

    //region Delete
    @ApiOperation(value = "Delete specific sale from the database", response = boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "sale has been removed successfully!"),
            @ApiResponse(code = 204, message = "sale already had been removed from the database!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "{Id}")
    public boolean deleteSale(@PathVariable UUID Id) {

        boolean result = saleService.deleteSale(Id);

        return result;
    }
    //endregion
}
