package com.maids.cc.store.management.backend.controller;

import com.maids.cc.store.management.backend.dto.request.ProductRequestDto;
import com.maids.cc.store.management.backend.dto.response.ProductResponseDto;
import com.maids.cc.store.management.backend.service.ProductService;
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
@RequestMapping("/product")
@Api(value = "Product Management end-points", description = "Main CRUD end-points into Product")
public class ProductController {

    //region Properties
    @Autowired
    ProductService productService;
    //endregion

    //region Get By Id
    @ApiOperation(value = "retrieve specific product from the database", response = ProductResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the product"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "{Id}")
    public ResponseEntity<ProductResponseDto> get(@PathVariable UUID Id) {

        ResponseEntity<ProductResponseDto> result = productService.getProduct(Id);

        return result;
    }
    //endregion

    //region Get All
    @ApiOperation(value = "retrieve all the products from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved products list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAll() {

        ResponseEntity<List<ProductResponseDto>> result = productService.getAllProducts();

        return result;
    }
    //endregion

    //region Post
    @ApiOperation(value = "add a new product to the database", response = ProductResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "product has been added successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto dto) {

        ResponseEntity<ProductResponseDto> result = productService.addProduct(dto);

        return result;
    }
    //endregion

    //region Put
    @ApiOperation(value = "update a product to the database", response = ProductResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "product has been updated successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "{Id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID Id, @RequestBody ProductRequestDto dto) {

        ResponseEntity<ProductResponseDto> result = productService.updateProduct(Id, dto);

        return result;
    }
    //endregion

    //region Delete
    @ApiOperation(value = "Delete specific product from the database", response = boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "product has been removed successfully!"),
            @ApiResponse(code = 204, message = "product already had been removed from the database!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "{Id}")
    public boolean deleteProduct(@PathVariable UUID Id) {

        boolean result = productService.deleteProduct(Id);

        return result;
    }
    //endregion
}
