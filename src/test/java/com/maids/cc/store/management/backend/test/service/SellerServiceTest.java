package com.maids.cc.store.management.backend.test.service;

import com.maids.cc.store.management.backend.dto.request.SellerRequestDto;
import com.maids.cc.store.management.backend.dto.response.SellerResponseDto;
import com.maids.cc.store.management.backend.entity.Seller;
import com.maids.cc.store.management.backend.repository.SellerRepository;
import com.maids.cc.store.management.backend.service.SellerService;
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
public class SellerServiceTest {

    //region Properties
    @InjectMocks
    private SellerService sellerService;

    @Mock
    private SellerRepository sellerRepository;

    private Seller actualSeller;

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
        String firstName = "seller first name";
        String lastName = "seller last name";
        String mobileNumber = "seller mobile number";
        //endregion

        //region entity
        actualSeller = new Seller();
        actualSeller.setId(id);
        actualSeller.setFirstName(firstName);
        actualSeller.setLastName(lastName);
        actualSeller.setMobileNumber(mobileNumber);
        //endregion

        //region list of entity
        List<Seller> sellersList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            sellersList.add(actualSeller);
        //endregion

        //region mock actions

        //region add seller case
        when(sellerRepository.save(any(Seller.class))).thenReturn(actualSeller);
        //endregion

        //region get seller case
        when(sellerRepository.get(id)).thenReturn(actualSeller);
        //endregion

        //region delete seller case
        doNothing().when(sellerRepository).delete(id);
        //endregion

        //region get all sellers case
        when(sellerRepository.getAll()).thenReturn(sellersList);
        //endregion

        //endregion
    }

    //region Tests
    @Test
    public void testSellerAdd(){
        SellerRequestDto sellerRequestDto = new SellerRequestDto();
        sellerRequestDto.firstName = actualSeller.getFirstName();
        sellerRequestDto.lastName = actualSeller.getLastName();
        sellerRequestDto.mobileNumber = actualSeller.getMobileNumber();


        SellerResponseDto addedSeller = sellerService.addSeller(sellerRequestDto).getBody();

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

        SellerResponseDto updatedSeller = sellerService.updateSeller(actualSeller.getId(), sellerRequestDto).getBody();

        assertNotNull(updatedSeller);
        assertNotNull(updatedSeller.id);
        assertEquals(updatedSeller.firstName, sellerRequestDto.firstName);
        assertEquals(updatedSeller.lastName, sellerRequestDto.lastName);
        assertEquals(updatedSeller.mobileNumber, sellerRequestDto.mobileNumber);
    }

    @Test
    public void testSellerGet(){
        SellerResponseDto expectedSeller = sellerService.getSeller(actualSeller.getId()).getBody();

        assertNotNull(expectedSeller);
        assertEquals(expectedSeller.id, actualSeller.getId());
        assertEquals(expectedSeller.firstName, actualSeller.getFirstName());
        assertEquals(expectedSeller.lastName, actualSeller.getLastName());
        assertEquals(expectedSeller.mobileNumber, actualSeller.getMobileNumber());
    }

    @Test
    public void testSellerGetAll(){
        List<SellerResponseDto> list = sellerService.getAllSellers().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(sellerRepository, times(1)).getAll();
    }

    @Test
    public void testSellerDelete(){
        sellerService.deleteSeller(actualSeller.getId());

        verify(sellerRepository,times(1)).delete(actualSeller.getId());
    }
    //endregion
}
