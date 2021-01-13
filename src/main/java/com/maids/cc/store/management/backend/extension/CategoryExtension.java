package com.maids.cc.store.management.backend.extension;

import com.maids.cc.store.management.backend.dto.request.CategoryRequestDto;
import com.maids.cc.store.management.backend.dto.response.CategoryResponseDto;
import com.maids.cc.store.management.backend.entity.Category;

public class CategoryExtension {

    public static Category toCategoryEntity(CategoryRequestDto categoryRequestDto){

        Category category = new Category();

        category.setName(categoryRequestDto.name);
        category.setDescription(categoryRequestDto.description);

        return category;
    }

    public static CategoryResponseDto toCategoryDto(Category category){

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();

        categoryResponseDto.id = category.getId();
        categoryResponseDto.name = category.getName();
        categoryResponseDto.description = category.getDescription();

        return categoryResponseDto;
    }
}
