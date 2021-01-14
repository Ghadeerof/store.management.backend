package com.maids.cc.store.management.backend.test.controller;

import com.maids.cc.store.management.backend.entity.Product;
import com.maids.cc.store.management.backend.service.ProductService;
import com.maids.cc.store.management.backend.controller.ProductController;
import com.maids.cc.store.management.backend.dto.request.ProductRequestDto;
import com.maids.cc.store.management.backend.dto.response.ProductResponseDto;

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
public class ProductControllerTest {
    
    //region Properties
    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private Product actualProduct;

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
        String name = "product name";
        String description = "product description";
        float price = 500;
        Integer quantity = 10;
        //endregion

        //region entity
        actualProduct = new Product();
        actualProduct.setId(id);
        actualProduct.setName(name);
        actualProduct.setDescription(description);
        actualProduct.setPrice(price);
        actualProduct.setQuantity(quantity);
        //endregion

        //region responseDto
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.id = id;
        productResponseDto.name = name;
        productResponseDto.description = description;
        productResponseDto.price = price;
        productResponseDto.quantity = quantity;

        ResponseEntity<ProductResponseDto> responseEntity = new ResponseEntity<>(productResponseDto, HttpStatus.ACCEPTED);
        //endregion

        //region list of entity
        List<ProductResponseDto> productsList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            productsList.add(productResponseDto);

        ResponseEntity<List<ProductResponseDto>> responseEntityList =
                new ResponseEntity<>(productsList,HttpStatus.ACCEPTED);
        //endregion

        //region mock actions

        //region add Product case
        when(productService.addProduct(any(ProductRequestDto.class))).thenReturn(responseEntity);
        //endregion

        //region update Product case
        when(productService.updateProduct(any(UUID.class),any(ProductRequestDto.class))).thenReturn(responseEntity);
        //endregion

        //region get Product case
        when(productService.getProduct(id)).thenReturn(responseEntity);
        //endregion

        //region delete Product case
        when(productService.deleteProduct(id)).thenReturn(true);
        //endregion

        //region get all Products case
        when(productService.getAllProducts()).thenReturn(responseEntityList);
        //endregion

        //endregion
    }
    //endregion

    //region Tests
    @Test
    public void testProductAdd(){
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.name = actualProduct.getName();
        productRequestDto.description = actualProduct.getDescription();
        productRequestDto.price = actualProduct.getPrice();
        productRequestDto.quantity = actualProduct.getQuantity();

        ProductResponseDto addedProduct = productController.addProduct(productRequestDto).getBody();

        assertNotNull(addedProduct);
        assertNotNull(addedProduct.id);
        assertEquals(addedProduct.name, productRequestDto.name);
        assertEquals(addedProduct.description, productRequestDto.description);
        assertEquals(addedProduct.price, productRequestDto.price,1);
        assertEquals(addedProduct.quantity, productRequestDto.quantity);
    }

    @Test
    public void testProductUpdate(){
        ProductRequestDto productRequestDto = new ProductRequestDto();

        productRequestDto.name = actualProduct.getName();
        productRequestDto.description = actualProduct.getDescription();
        productRequestDto.price = actualProduct.getPrice();
        productRequestDto.quantity = actualProduct.getQuantity();

        ProductResponseDto updatedProduct = productController.updateProduct(actualProduct.getId(), productRequestDto).getBody();

        assertNotNull(updatedProduct);
        assertNotNull(updatedProduct.id);
        assertEquals(updatedProduct.name, productRequestDto.name);
        assertEquals(updatedProduct.description, productRequestDto.description);
        assertEquals(updatedProduct.price, productRequestDto.price,1);
        assertEquals(updatedProduct.quantity, productRequestDto.quantity);
    }

    @Test
    public void testProductGet(){
        ProductResponseDto expectedProduct = productController.get(actualProduct.getId()).getBody();

        assertNotNull(expectedProduct);
        assertEquals(expectedProduct.id, actualProduct.getId());
        assertEquals(expectedProduct.name, actualProduct.getName());
        assertEquals(expectedProduct.description, actualProduct.getDescription());
        assertEquals(expectedProduct.price, actualProduct.getPrice(),1);
        assertEquals(expectedProduct.quantity, actualProduct.getQuantity());
    }

    @Test
    public void testProductGetAll(){
        List<ProductResponseDto> list = productController.getAll().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testProductDelete(){
        Boolean expectedResult = productController.deleteProduct(actualProduct.getId());

        assertEquals(expectedResult, true);
        verify(productService,times(1)).deleteProduct(actualProduct.getId());
    }
    //endregion
}
