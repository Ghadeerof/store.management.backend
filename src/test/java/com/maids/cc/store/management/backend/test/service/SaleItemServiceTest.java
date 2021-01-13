package com.maids.cc.store.management.backend.test.service;

import com.maids.cc.store.management.backend.dto.request.SaleItemRequestDto;
import com.maids.cc.store.management.backend.dto.response.SaleItemResponseDto;
import com.maids.cc.store.management.backend.entity.SaleItem;
import com.maids.cc.store.management.backend.repository.SaleItemRepository;
import com.maids.cc.store.management.backend.service.SaleItemService;
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
public class SaleItemServiceTest {

    //region Properties
    @InjectMocks
    private SaleItemService saleItemService;

    @Mock
    private SaleItemRepository saleItemRepository;

    private SaleItem actualSaleItem;

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
        float price = 500;
        Integer quantity = 10;
        //endregion

        //region entity
        actualSaleItem = new SaleItem();
        actualSaleItem.setId(id);
        actualSaleItem.setPrice(price);
        actualSaleItem.setQuantity(quantity);
        //endregion

        //region list of entity
        List<SaleItem> saleItemsList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            saleItemsList.add(actualSaleItem);
        //endregion

        //region mock actions

        //region add sale items case
        when(saleItemRepository.save(any(SaleItem.class))).thenReturn(actualSaleItem);
        //endregion

        //region get sale items case
        when(saleItemRepository.get(id)).thenReturn(actualSaleItem);
        //endregion

        //region delete sale items case
        doNothing().when(saleItemRepository).delete(id);
        //endregion

        //region get all sale items case
        when(saleItemRepository.getAll()).thenReturn(saleItemsList);
        //endregion

        //endregion
    }

    //region Tests
    @Test
    public void testSaleItemAdd(){
        SaleItemRequestDto saleItemRequestDto = new SaleItemRequestDto();
        saleItemRequestDto.price = actualSaleItem.getPrice();
        saleItemRequestDto.quantity = actualSaleItem.getQuantity();

        SaleItemResponseDto addedSaleItem = saleItemService.addSaleItem(saleItemRequestDto).getBody();

        assertNotNull(addedSaleItem);
        assertNotNull(addedSaleItem.id);
        assertEquals(addedSaleItem.price, saleItemRequestDto.price,1);
        assertEquals(addedSaleItem.quantity, saleItemRequestDto.quantity);
    }

    @Test
    public void testSaleItemUpdate(){
        SaleItemRequestDto saleItemRequestDto = new SaleItemRequestDto();

        saleItemRequestDto.price = actualSaleItem.getPrice();
        saleItemRequestDto.quantity = actualSaleItem.getQuantity();

        SaleItemResponseDto updatedSaleItem = saleItemService.updateSaleItem(actualSaleItem.getId(), saleItemRequestDto).getBody();

        assertNotNull(updatedSaleItem);
        assertNotNull(updatedSaleItem.id);
        assertEquals(updatedSaleItem.price, saleItemRequestDto.price, 1);
        assertEquals(updatedSaleItem.quantity, saleItemRequestDto.quantity);
    }

    @Test
    public void testSaleItemGet(){
        SaleItemResponseDto expectedSaleItem = saleItemService.getSaleItem(actualSaleItem.getId()).getBody();

        assertNotNull(expectedSaleItem);
        assertEquals(expectedSaleItem.id, actualSaleItem.getId());
        assertEquals(expectedSaleItem.price, actualSaleItem.getPrice(),1);
        assertEquals(expectedSaleItem.quantity, actualSaleItem.getQuantity());
    }

    @Test
    public void testSaleItemGetAll(){
        List<SaleItemResponseDto> list = saleItemService.getAllSaleItems().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(saleItemRepository, times(1)).getAll();
    }

    @Test
    public void testSaleItemDelete(){
        saleItemService.deleteSaleItem(actualSaleItem.getId());

        verify(saleItemRepository,times(1)).delete(actualSaleItem.getId());
    }
    //endregion
}
