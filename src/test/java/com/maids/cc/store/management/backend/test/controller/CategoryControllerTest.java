package com.maids.cc.store.management.backend.test.controller;

import com.maids.cc.store.management.backend.entity.Category;
import com.maids.cc.store.management.backend.service.CategoryService;
import com.maids.cc.store.management.backend.controller.CategoryController;
import com.maids.cc.store.management.backend.dto.request.CategoryRequestDto;
import com.maids.cc.store.management.backend.dto.response.CategoryResponseDto;

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
public class CategoryControllerTest {
    
    //region Properties
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private Category actualCategory;

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
        String name = "category name";
        String description = "category description";
        //endregion

        //region entity
        actualCategory = new Category();
        actualCategory.setId(id);
        actualCategory.setName(name);
        actualCategory.setDescription(description);
        //endregion

        //region responseDto
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.id = id;
        categoryResponseDto.name = name;
        categoryResponseDto.description = description;

        ResponseEntity<CategoryResponseDto> responseEntity = new ResponseEntity<>(categoryResponseDto, HttpStatus.ACCEPTED);
        //endregion

        //region list of entity
        List<CategoryResponseDto> categoriesList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            categoriesList.add(categoryResponseDto);

        ResponseEntity<List<CategoryResponseDto>> responseEntityList =
                new ResponseEntity<>(categoriesList,HttpStatus.ACCEPTED);
        //endregion

        //region mock actions

        //region add category case
        when(categoryService.addCategory(any(CategoryRequestDto.class))).thenReturn(responseEntity);
        //endregion

        //region update category case
        when(categoryService.updateCategory(any(UUID.class),any(CategoryRequestDto.class))).thenReturn(responseEntity);
        //endregion        

        //region get category case
        when(categoryService.getCategory(id)).thenReturn(responseEntity);
        //endregion

        //region delete category case
        when(categoryService.deleteCategory(id)).thenReturn(true);
        //endregion

        //region get all categories case
        when(categoryService.getAllCategories()).thenReturn(responseEntityList);
        //endregion

        //endregion
    }
    //endregion    

    //region Tests
    @Test
    public void testCategoryAdd(){
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.name = actualCategory.getName();
        categoryRequestDto.description = actualCategory.getDescription();

        CategoryResponseDto addedCategory = categoryController.addCategory(categoryRequestDto).getBody();

        assertNotNull(addedCategory);
        assertNotNull(addedCategory.id);
        assertEquals(addedCategory.name, categoryRequestDto.name);
        assertEquals(addedCategory.description, categoryRequestDto.description);
    }

    @Test
    public void testCategoryUpdate(){
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.name = actualCategory.getName();
        categoryRequestDto.description = actualCategory.getDescription();

        CategoryResponseDto updatedCategory = categoryController.updateCategory(actualCategory.getId(), categoryRequestDto).getBody();

        assertNotNull(updatedCategory);
        assertNotNull(updatedCategory.id);
        assertEquals(updatedCategory.name, categoryRequestDto.name);
        assertEquals(updatedCategory.description, categoryRequestDto.description);
    }

    @Test
    public void testCategoryGet(){
        CategoryResponseDto expectedCategory = categoryController.get(actualCategory.getId()).getBody();

        assertNotNull(expectedCategory);
        assertEquals(expectedCategory.id, actualCategory.getId());
        assertEquals(expectedCategory.name, actualCategory.getName());
        assertEquals(expectedCategory.description, actualCategory.getDescription());
    }

    @Test
    public void testCategoryGetAll(){
        List<CategoryResponseDto> list = categoryController.getAll().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void testCategoryDelete(){
        Boolean expectedResult = categoryController.deleteCategory(actualCategory.getId());

        assertEquals(expectedResult, true);
        verify(categoryService,times(1)).deleteCategory(actualCategory.getId());
    }
    //endregion  
}
