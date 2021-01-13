package com.maids.cc.store.management.backend.test.service;

import com.maids.cc.store.management.backend.dto.request.ProductRequestDto;
import com.maids.cc.store.management.backend.dto.response.ProductResponseDto;
import com.maids.cc.store.management.backend.entity.Product;
import com.maids.cc.store.management.backend.repository.ProductRepository;
import com.maids.cc.store.management.backend.service.ProductService;
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
public class ProductServiceTest {

    //region Properties
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product actualProduct;

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

        //region list of entity
        List<Product> productsList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            productsList.add(actualProduct);
        //endregion

        //region mock actions

        //region add product case
        when(productRepository.save(any(Product.class))).thenReturn(actualProduct);
        //endregion

        //region get product case
        when(productRepository.get(id)).thenReturn(actualProduct);
        //endregion

        //region delete product case
        doNothing().when(productRepository).delete(id);
        //endregion

        //region get all products case
        when(productRepository.getAll()).thenReturn(productsList);
        //endregion

        //endregion
    }

    //region Tests
    @Test
    public void testProductAdd(){
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.name = actualProduct.getName();
        productRequestDto.description = actualProduct.getDescription();
        productRequestDto.price = actualProduct.getPrice();
        productRequestDto.quantity = actualProduct.getQuantity();

        ProductResponseDto addedProduct = productService.addProduct(productRequestDto).getBody();

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

        ProductResponseDto updatedProduct = productService.updateProduct(actualProduct.getId(), productRequestDto).getBody();

        assertNotNull(updatedProduct);
        assertNotNull(updatedProduct.id);
        assertEquals(updatedProduct.name, productRequestDto.name);
        assertEquals(updatedProduct.description, productRequestDto.description);
        assertEquals(updatedProduct.price, productRequestDto.price, 1);
        assertEquals(updatedProduct.quantity, productRequestDto.quantity);
    }

    @Test
    public void testProductGet(){
        ProductResponseDto expectedProduct = productService.getProduct(actualProduct.getId()).getBody();

        assertNotNull(expectedProduct);
        assertEquals(expectedProduct.id, actualProduct.getId());
        assertEquals(expectedProduct.name, actualProduct.getName());
        assertEquals(expectedProduct.description, actualProduct.getDescription());
        assertEquals(expectedProduct.price, actualProduct.getPrice(),1);
        assertEquals(expectedProduct.quantity, actualProduct.getQuantity());
    }

    @Test
    public void testProductGetAll(){
        List<ProductResponseDto> list = productService.getAllProducts().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(productRepository, times(1)).getAll();
    }

    @Test
    public void testProductDelete(){
        productService.deleteProduct(actualProduct.getId());

        verify(productRepository,times(1)).delete(actualProduct.getId());
    }
    //endregion
}
