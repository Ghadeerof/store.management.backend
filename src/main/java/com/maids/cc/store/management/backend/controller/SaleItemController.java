package com.maids.cc.store.management.backend.controller;

import com.maids.cc.store.management.backend.dto.request.ProductRequestDto;
import com.maids.cc.store.management.backend.dto.request.SaleItemRequestDto;
import com.maids.cc.store.management.backend.dto.response.ProductResponseDto;
import com.maids.cc.store.management.backend.dto.response.SaleItemResponseDto;
import com.maids.cc.store.management.backend.service.SaleItemService;
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
@RequestMapping("/sale-item")
@Api(value = "SaleItem Management end-points", description = "Main CRUD end-points into SaleItem")
public class SaleItemController {

    //region Properties
    @Autowired
    SaleItemService saleItemService;
    //endregion

    //region Get By Id
    @ApiOperation(value = "retrieve specific sale item from the database", response = SaleItemResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the sale item"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "{Id}")
    public ResponseEntity<SaleItemResponseDto> get(@PathVariable UUID Id) {

        ResponseEntity<SaleItemResponseDto> result = saleItemService.getSaleItem(Id);

        return result;
    }
    //endregion

    //region Get All
    @ApiOperation(value = "retrieve all the saleItems from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sale items list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<List<SaleItemResponseDto>> getAll() {

        ResponseEntity<List<SaleItemResponseDto>> result = saleItemService.getAllSaleItems();

        return result;
    }

    @ApiOperation(value = "retrieve all the sale items for specific sale from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sale items list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "get-sale-items-of-sale/{saleId}")
    public ResponseEntity<List<SaleItemResponseDto>> getAllSaleItemsOfSale(@PathVariable UUID saleId) {

        ResponseEntity<List<SaleItemResponseDto>> result = saleItemService.getAllSaleItems(saleId);

        return result;
    }

    @ApiOperation(value = "retrieve all the sale items for specific product from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved sale items list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "get-sale-items-of-product/{productId}")
    public ResponseEntity<List<SaleItemResponseDto>> getAllSaleItemOfProduct(@PathVariable UUID productId) {

        ResponseEntity<List<SaleItemResponseDto>> result = saleItemService.getAllSaleItemsOfProduct(productId);

        return result;
    }
    //endregion

    //region Post
    @ApiOperation(value = "add a new sale item to the database", response = SaleItemResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "sale item has been added successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    public ResponseEntity<SaleItemResponseDto> addSaleItem(@RequestBody SaleItemRequestDto dto) {

        ResponseEntity<SaleItemResponseDto> result = saleItemService.addSaleItem(dto);

        return result;
    }
    //endregion

    //region Put
    @ApiOperation(value = "update a sale item to the database", response = SaleItemResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "sale item has been updated successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "{Id}")
    public ResponseEntity<SaleItemResponseDto> updateSaleItem(@PathVariable UUID Id, @RequestBody SaleItemRequestDto dto) {

        ResponseEntity<SaleItemResponseDto> result = saleItemService.updateSaleItem(Id, dto);

        return result;
    }
    //endregion

    //region Delete
    @ApiOperation(value = "Delete specific sale item from the database", response = boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "sale item has been removed successfully!"),
            @ApiResponse(code = 204, message = "sale item already had been removed from the database!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "{Id}")
    public boolean deleteSaleItem(@PathVariable UUID Id) {

        boolean result = saleItemService.deleteSaleItem(Id);

        return result;
    }
    //endregion
}
