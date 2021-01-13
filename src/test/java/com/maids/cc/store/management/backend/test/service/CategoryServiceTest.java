package com.maids.cc.store.management.backend.test.service;

import com.maids.cc.store.management.backend.dto.request.CategoryRequestDto;
import com.maids.cc.store.management.backend.dto.response.CategoryResponseDto;
import com.maids.cc.store.management.backend.entity.Category;
import com.maids.cc.store.management.backend.repository.CategoryRepository;
import com.maids.cc.store.management.backend.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    //region Properties
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category actualCategory;

    private Integer listSize;
    //endregion

    //region Initialization
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        seedData();
    }

    private void seedData(){
        //region used values
        listSize = 5;

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

        //region list of entity
        List<Category> categoriesList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            categoriesList.add(actualCategory);
        //endregion

        //region mock actions

        //region add category case
        when(categoryRepository.save(any(Category.class))).thenReturn(actualCategory);
        //endregion

        //region get category case
        when(categoryRepository.get(id)).thenReturn(actualCategory);
        //endregion

        //region delete category case
        doNothing().when(categoryRepository).delete(id);
        //endregion

        //region get all categories case
        when(categoryRepository.getAll()).thenReturn(categoriesList);
        //endregion

        //endregion
    }

    //region Tests
    @Test
    public void testCategoryAdd(){
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.name = actualCategory.getName();
        categoryRequestDto.description = actualCategory.getDescription();

        CategoryResponseDto addedCategory = categoryService.addCategory(categoryRequestDto).getBody();

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

        CategoryResponseDto updatedCategory = categoryService.updateCategory(actualCategory.getId(), categoryRequestDto).getBody();

        assertNotNull(updatedCategory);
        assertNotNull(updatedCategory.id);
        assertEquals(updatedCategory.name, categoryRequestDto.name);
        assertEquals(updatedCategory.description, categoryRequestDto.description);
    }

    @Test
    public void testCategoryGet(){
        CategoryResponseDto expectedCategory = categoryService.getCategory(actualCategory.getId()).getBody();

        assertNotNull(expectedCategory);
        assertEquals(expectedCategory.id, actualCategory.getId());
        assertEquals(expectedCategory.name, actualCategory.getName());
        assertEquals(expectedCategory.description, actualCategory.getDescription());
    }

    @Test
    public void testCategoryGetAll(){
        List<CategoryResponseDto> list = categoryService.getAllCategories().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(categoryRepository, times(1)).getAll();
    }

    @Test
    public void testCategoryDelete(){
        categoryService.deleteCategory(actualCategory.getId());

        verify(categoryRepository,times(1)).delete(actualCategory.getId());
    }
    //endregion
}
