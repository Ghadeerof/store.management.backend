package com.maids.cc.store.management.backend.service;

import com.maids.cc.store.management.backend.dto.request.CategoryRequestDto;
import com.maids.cc.store.management.backend.dto.response.CategoryResponseDto;
import com.maids.cc.store.management.backend.entity.Category;
import com.maids.cc.store.management.backend.extension.CategoryExtension;
import com.maids.cc.store.management.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public ResponseEntity<CategoryResponseDto> getCategory(UUID id){

        try {
            Category category = categoryRepository.get(id);

            if(category == null){
                return new ResponseEntity<>(null, HttpStatus.FAILED_DEPENDENCY);
            }

            CategoryResponseDto categoryResponseDto = CategoryExtension.toCategoryDto(category);

            return new ResponseEntity<>(categoryResponseDto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        try {
            List<Category> categories = categoryRepository.getAll();

            List<CategoryResponseDto> categoryResponseDtos = categories.stream()
                    .map(CategoryExtension::toCategoryDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(categoryResponseDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<CategoryResponseDto> addCategory(CategoryRequestDto dto){
        try {
            Category category = CategoryExtension.toCategoryEntity(dto);
            Category addedCategory = categoryRepository.save(category);

            CategoryResponseDto responseDto = CategoryExtension.toCategoryDto(addedCategory);

            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<CategoryResponseDto> updateCategory(UUID id, CategoryRequestDto dto){

        try {
            Category category = categoryRepository.get(id);

            if(category == null){
                return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
            }

            category.setName(dto.name);
            category.setDescription(dto.description);

            Category addedCategory = categoryRepository.save(category);

            CategoryResponseDto responseDto = CategoryExtension.toCategoryDto(addedCategory);

            return new ResponseEntity<>(responseDto,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public boolean deleteCategory(UUID id){
        Category category = categoryRepository.get(id);

        if(category == null){
            return false;
        }

        categoryRepository.delete(id);

        return true;
    }
}
