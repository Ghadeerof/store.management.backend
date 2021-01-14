package com.maids.cc.store.management.backend.test.controller;

import com.maids.cc.store.management.backend.entity.Seller;
import com.maids.cc.store.management.backend.service.SellerService;
import com.maids.cc.store.management.backend.controller.SellerController;
import com.maids.cc.store.management.backend.dto.request.SellerRequestDto;
import com.maids.cc.store.management.backend.dto.response.SellerResponseDto;

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
public class SellerControllerTest {

    //region Properties
    @InjectMocks
    private SellerController sellerController;

    @Mock
    private SellerService sellerService;

    private Seller actualSeller;

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
        String firstName = "Seller first name";
        String lastName = "Seller last name";
        String mobileNumber = "Seller mobile number";
        //endregion

        //region entity
        actualSeller = new Seller();
        actualSeller.setId(id);
        actualSeller.setFirstName(firstName);
        actualSeller.setLastName(lastName);
        actualSeller.setMobileNumber(mobileNumber);
        //endregion

        //region responseDto
        SellerResponseDto sellerResponseDto = new SellerResponseDto();
        sellerResponseDto.id = id;
        sellerResponseDto.firstName = firstName;
        sellerResponseDto.lastName = lastName;
        sellerResponseDto.mobileNumber = mobileNumber;

        ResponseEntity<SellerResponseDto> responseEntity = new ResponseEntity<>(sellerResponseDto, HttpStatus.ACCEPTED);
        //endregion

        //region list of entity
        List<SellerResponseDto> SellersList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            SellersList.add(sellerResponseDto);

        ResponseEntity<List<SellerResponseDto>> responseEntityList =
                new ResponseEntity<>(SellersList,HttpStatus.ACCEPTED);
        //endregion

        //region mock actions

        //region add Seller case
        when(sellerService.addSeller(any(SellerRequestDto.class))).thenReturn(responseEntity);
        //endregion

        //region update Seller case
        when(sellerService.updateSeller(any(UUID.class),any(SellerRequestDto.class))).thenReturn(responseEntity);
        //endregion

        //region get Seller case
        when(sellerService.getSeller(id)).thenReturn(responseEntity);
        //endregion

        //region delete Seller case
        when(sellerService.deleteSeller(id)).thenReturn(true);
        //endregion

        //region get all Sellers case
        when(sellerService.getAllSellers()).thenReturn(responseEntityList);
        //endregion

        //endregion
    }
    //endregion

    //region Tests
    @Test
    public void testSellerAdd(){
        SellerRequestDto sellerRequestDto = new SellerRequestDto();
        sellerRequestDto.firstName = actualSeller.getFirstName();
        sellerRequestDto.lastName = actualSeller.getLastName();
        sellerRequestDto.mobileNumber = actualSeller.getMobileNumber();

        SellerResponseDto addedSeller = sellerController.addSeller(sellerRequestDto).getBody();

        assertNotNull(addedSeller);
        assertNotNull(addedSeller.id);
        assertEquals(addedSeller.firstName, sellerRequestDto.firstName);
        assertEquals(addedSeller.lastName, sellerRequestDto.lastName);
        assertEquals(addedSeller.mobileNumber, sellerRequestDto.mobileNumber);
    }

    @Test
    public void testSellerUpdate(){
        SellerRequestDto sellerRequestDto = new SellerRequestDto();
        sellerRequestDto.firstName = actualSeller.getFirstName();
        sellerRequestDto.lastName = actualSeller.getLastName();
        sellerRequestDto.mobileNumber = actualSeller.getMobileNumber();

        SellerResponseDto updatedSeller = sellerController.updateSeller(actualSeller.getId(), sellerRequestDto).getBody();

        assertNotNull(updatedSeller);
        assertNotNull(updatedSeller.id);
        assertEquals(updatedSeller.firstName, sellerRequestDto.firstName);
        assertEquals(updatedSeller.lastName, sellerRequestDto.lastName);
        assertEquals(updatedSeller.mobileNumber, sellerRequestDto.mobileNumber);
    }

    @Test
    public void testSellerGet(){
        SellerResponseDto expectedSeller = sellerController.get(actualSeller.getId()).getBody();

        assertNotNull(expectedSeller);
        assertEquals(expectedSeller.id, actualSeller.getId());
        assertEquals(expectedSeller.firstName, actualSeller.getFirstName());
        assertEquals(expectedSeller.lastName, actualSeller.getLastName());
        assertEquals(expectedSeller.mobileNumber, actualSeller.getMobileNumber());
    }

    @Test
    public void testSellerGetAll(){
        List<SellerResponseDto> list = sellerController.getAll().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(sellerService, times(1)).getAllSellers();
    }

    @Test
    public void testSellerDelete(){
        Boolean expectedResult = sellerController.deleteSeller(actualSeller.getId());

        assertEquals(expectedResult, true);
        verify(sellerService,times(1)).deleteSeller(actualSeller.getId());
    }
    //endregion
}
