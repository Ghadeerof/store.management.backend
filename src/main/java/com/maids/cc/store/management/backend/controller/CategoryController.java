package com.maids.cc.store.management.backend.controller;

import com.maids.cc.store.management.backend.dto.request.CategoryRequestDto;
import com.maids.cc.store.management.backend.dto.response.CategoryResponseDto;
import com.maids.cc.store.management.backend.service.CategoryService;
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
@RequestMapping("/category")
@Api(value = "Category Management end-points", description = "Main CRUD end-points into Category")
public class CategoryController {

    //region Properties
    @Autowired
    CategoryService categoryService;
    //endregion

    //region Get By Id
    @ApiOperation(value = "retrieve specific category from the database", response = CategoryResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the category"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "{Id}")
    public ResponseEntity<CategoryResponseDto> get(@PathVariable UUID Id) {

        ResponseEntity<CategoryResponseDto> result = categoryService.getCategory(Id);

        return result;
    }
    //endregion

    //region Get All
    @ApiOperation(value = "retrieve all the categories from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved categories list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAll() {

        ResponseEntity<List<CategoryResponseDto>> result = categoryService.getAllCategories();

        return result;
    }
    //endregion

    //region Post
    @ApiOperation(value = "add a new category to the database", response = CategoryResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "category has been added successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    public ResponseEntity<CategoryResponseDto> addCategory(@RequestBody CategoryRequestDto dto) {

        ResponseEntity<CategoryResponseDto> result = categoryService.addCategory(dto);

        return result;
    }
    //endregion

    //region Put
    @ApiOperation(value = "update a category to the database", response = CategoryResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " category has been updated successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "{Id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable UUID Id, @RequestBody CategoryRequestDto dto) {

        ResponseEntity<CategoryResponseDto> result = categoryService.updateCategory(Id, dto);

        return result;
    }
    //endregion

    //region Delete
    @ApiOperation(value = "Delete specific category from the database", response = boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "category has been removed successfully!"),
            @ApiResponse(code = 204, message = "category already had been removed from the database!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "{Id}")
    public boolean deleteCategory(@PathVariable UUID Id) {

        boolean result = categoryService.deleteCategory(Id);

        return result;
    }
    //endregion
}
