package com.maids.cc.store.management.backend.test.controller;


import com.maids.cc.store.management.backend.entity.Client;
import com.maids.cc.store.management.backend.service.ClientService;
import com.maids.cc.store.management.backend.controller.ClientController;
import com.maids.cc.store.management.backend.dto.request.ClientRequestDto;
import com.maids.cc.store.management.backend.dto.response.ClientResponseDto;

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
public class ClientControllerTest {

    //region Properties
    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    private Client actualClient;

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
        String firstName = "client first name";
        String lastName = "client last name";
        String mobileNumber = "client mobile number";
        //endregion

        //region entity
        actualClient = new Client();
        actualClient.setId(id);
        actualClient.setFirstName(firstName);
        actualClient.setLastName(lastName);
        actualClient.setMobileNumber(mobileNumber);
        //endregion

        //region responseDto
        ClientResponseDto clientResponseDto = new ClientResponseDto();
        clientResponseDto.id = id;
        clientResponseDto.firstName = firstName;
        clientResponseDto.lastName = lastName;
        clientResponseDto.mobileNumber = mobileNumber;

        ResponseEntity<ClientResponseDto> responseEntity = new ResponseEntity<>(clientResponseDto, HttpStatus.ACCEPTED);
        //endregion

        //region list of entity
        List<ClientResponseDto> clientsList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            clientsList.add(clientResponseDto);

        ResponseEntity<List<ClientResponseDto>> responseEntityList =
                new ResponseEntity<>(clientsList,HttpStatus.ACCEPTED);
        //endregion

        //region mock actions

        //region add client case
        when(clientService.addClient(any(ClientRequestDto.class))).thenReturn(responseEntity);
        //endregion

        //region update client case
        when(clientService.updateClient(any(UUID.class),any(ClientRequestDto.class))).thenReturn(responseEntity);
        //endregion

        //region get client case
        when(clientService.getClient(id)).thenReturn(responseEntity);
        //endregion

        //region delete client case
        when(clientService.deleteClient(id)).thenReturn(true);
        //endregion

        //region get all clients case
        when(clientService.getAllClients()).thenReturn(responseEntityList);
        //endregion

        //endregion
    }
    //endregion

    //region Tests
    @Test
    public void testClientAdd(){
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.firstName = actualClient.getFirstName();
        clientRequestDto.lastName = actualClient.getLastName();
        clientRequestDto.mobileNumber = actualClient.getMobileNumber();

        ClientResponseDto addedClient = clientController.addClient(clientRequestDto).getBody();

        assertNotNull(addedClient);
        assertNotNull(addedClient.id);
        assertEquals(addedClient.firstName, clientRequestDto.firstName);
        assertEquals(addedClient.lastName, clientRequestDto.lastName);
        assertEquals(addedClient.mobileNumber, clientRequestDto.mobileNumber);
    }

    @Test
    public void testClientUpdate(){
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.firstName = actualClient.getFirstName();
        clientRequestDto.lastName = actualClient.getLastName();
        clientRequestDto.mobileNumber = actualClient.getMobileNumber();

        ClientResponseDto updatedClient = clientController.updateClient(actualClient.getId(), clientRequestDto).getBody();

        assertNotNull(updatedClient);
        assertNotNull(updatedClient.id);
        assertEquals(updatedClient.firstName, clientRequestDto.firstName);
        assertEquals(updatedClient.lastName, clientRequestDto.lastName);
        assertEquals(updatedClient.mobileNumber, clientRequestDto.mobileNumber);
    }

    @Test
    public void testClientGet(){
        ClientResponseDto expectedClient = clientController.get(actualClient.getId()).getBody();

        assertNotNull(expectedClient);
        assertEquals(expectedClient.id, actualClient.getId());
        assertEquals(expectedClient.firstName, actualClient.getFirstName());
        assertEquals(expectedClient.lastName, actualClient.getLastName());
        assertEquals(expectedClient.mobileNumber, actualClient.getMobileNumber());
    }

    @Test
    public void testClientGetAll(){
        List<ClientResponseDto> list = clientController.getAll().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(clientService, times(1)).getAllClients();
    }

    @Test
    public void testClientDelete(){
        Boolean expectedResult = clientController.deleteClient(actualClient.getId());

        assertEquals(expectedResult, true);
        verify(clientService,times(1)).deleteClient(actualClient.getId());
    }
    //endregion
}
