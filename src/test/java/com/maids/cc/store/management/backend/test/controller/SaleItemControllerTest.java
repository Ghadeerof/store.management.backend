package com.maids.cc.store.management.backend.test.controller;

import com.maids.cc.store.management.backend.entity.SaleItem;
import com.maids.cc.store.management.backend.service.SaleItemService;
import com.maids.cc.store.management.backend.controller.SaleItemController;
import com.maids.cc.store.management.backend.dto.request.SaleItemRequestDto;
import com.maids.cc.store.management.backend.dto.response.SaleItemResponseDto;

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
public class SaleItemControllerTest {

    //region Properties
    @InjectMocks
    private SaleItemController saleItemController;

    @Mock
    private SaleItemService saleItemService;

    private SaleItem actualSaleItem;

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
        float price = 500;
        Integer quantity = 10;
        //endregion

        //region entity
        actualSaleItem = new SaleItem();
        actualSaleItem.setId(id);
        actualSaleItem.setPrice(price);
        actualSaleItem.setQuantity(quantity);
        //endregion

        //region responseDto
        SaleItemResponseDto saleItemResponseDto = new SaleItemResponseDto();
        saleItemResponseDto.id = id;
        saleItemResponseDto.price = price;
        saleItemResponseDto.quantity = quantity;

        ResponseEntity<SaleItemResponseDto> responseEntity = new ResponseEntity<>(saleItemResponseDto, HttpStatus.ACCEPTED);
        //endregion

        //region list of entity
        List<SaleItemResponseDto> saleItemsList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            saleItemsList.add(saleItemResponseDto);

        ResponseEntity<List<SaleItemResponseDto>> responseEntityList =
                new ResponseEntity<>(saleItemsList,HttpStatus.ACCEPTED);
        //endregion

        //region mock actions

        //region add SaleItem case
        when(saleItemService.addSaleItem(any(SaleItemRequestDto.class))).thenReturn(responseEntity);
        //endregion

        //region update SaleItem case
        when(saleItemService.updateSaleItem(any(UUID.class),any(SaleItemRequestDto.class))).thenReturn(responseEntity);
        //endregion

        //region get SaleItem case
        when(saleItemService.getSaleItem(id)).thenReturn(responseEntity);
        //endregion

        //region delete SaleItem case
        when(saleItemService.deleteSaleItem(id)).thenReturn(true);
        //endregion

        //region get all SaleItems case
        when(saleItemService.getAllSaleItems()).thenReturn(responseEntityList);
        //endregion

        //endregion
    }
    //endregion

    //region Tests
    @Test
    public void testSaleItemAdd(){
        SaleItemRequestDto saleItemRequestDto = new SaleItemRequestDto();
        saleItemRequestDto.price = actualSaleItem.getPrice();
        saleItemRequestDto.quantity = actualSaleItem.getQuantity();

        SaleItemResponseDto addedSaleItem = saleItemController.addSaleItem(saleItemRequestDto).getBody();

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

        SaleItemResponseDto updatedSaleItem = saleItemController.updateSaleItem(actualSaleItem.getId(), saleItemRequestDto).getBody();

        assertNotNull(updatedSaleItem);
        assertNotNull(updatedSaleItem.id);
        assertEquals(updatedSaleItem.price, saleItemRequestDto.price,1);
        assertEquals(updatedSaleItem.quantity, saleItemRequestDto.quantity);
    }

    @Test
    public void testSaleItemGet(){
        SaleItemResponseDto expectedSaleItem = saleItemController.get(actualSaleItem.getId()).getBody();

        assertNotNull(expectedSaleItem);
        assertEquals(expectedSaleItem.id, actualSaleItem.getId());
        assertEquals(expectedSaleItem.price, actualSaleItem.getPrice(),1);
        assertEquals(expectedSaleItem.quantity, actualSaleItem.getQuantity());
    }

    @Test
    public void testSaleItemGetAll(){
        List<SaleItemResponseDto> list = saleItemController.getAll().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(saleItemService, times(1)).getAllSaleItems();
    }

    @Test
    public void testSaleItemDelete(){
        Boolean expectedResult = saleItemController.deleteSaleItem(actualSaleItem.getId());

        assertEquals(expectedResult, true);
        verify(saleItemService,times(1)).deleteSaleItem(actualSaleItem.getId());
    }
    //endregion
}
