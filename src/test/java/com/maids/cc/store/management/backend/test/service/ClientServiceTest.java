package com.maids.cc.store.management.backend.test.service;

import com.maids.cc.store.management.backend.dto.request.ClientRequestDto;
import com.maids.cc.store.management.backend.dto.response.ClientResponseDto;
import com.maids.cc.store.management.backend.entity.Client;
import com.maids.cc.store.management.backend.repository.ClientRepository;
import com.maids.cc.store.management.backend.service.ClientService;
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
public class ClientServiceTest {

    //region Properties
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private Client actualClient;

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

        //region list of entity
        List<Client> clientsList = new ArrayList<>();
        for(int i = 0; i < listSize; i++)
            clientsList.add(actualClient);
        //endregion

        //region mock actions

        //region add client case
        when(clientRepository.save(any(Client.class))).thenReturn(actualClient);
        //endregion

        //region get client case
        when(clientRepository.get(id)).thenReturn(actualClient);
        //endregion

        //region delete client case
        doNothing().when(clientRepository).delete(id);
        //endregion

        //region get all clients case
        when(clientRepository.getAll()).thenReturn(clientsList);
        //endregion

        //endregion
    }

    //region Tests
    @Test
    public void testClientAdd(){
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.firstName = actualClient.getFirstName();
        clientRequestDto.lastName = actualClient.getLastName();
        clientRequestDto.mobileNumber = actualClient.getMobileNumber();

        ClientResponseDto addedClient = clientService.addClient(clientRequestDto).getBody();

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

        ClientResponseDto updatedClient = clientService.updateClient(actualClient.getId(), clientRequestDto).getBody();

        assertNotNull(updatedClient);
        assertNotNull(updatedClient.id);
        assertEquals(updatedClient.firstName, clientRequestDto.firstName);
        assertEquals(updatedClient.lastName, clientRequestDto.lastName);
        assertEquals(updatedClient.mobileNumber, clientRequestDto.mobileNumber);
    }

    @Test
    public void testClientGet(){
        ClientResponseDto expectedClient = clientService.getClient(actualClient.getId()).getBody();

        assertNotNull(expectedClient);
        assertEquals(expectedClient.id, actualClient.getId());
        assertEquals(expectedClient.firstName, actualClient.getFirstName());
        assertEquals(expectedClient.lastName, actualClient.getLastName());
        assertEquals(expectedClient.mobileNumber, actualClient.getMobileNumber());
    }

    @Test
    public void testClientGetAll(){
        List<ClientResponseDto> list = clientService.getAllClients().getBody();

        assertNotNull(list);
        assertEquals(listSize, new Integer(list.size()));
        verify(clientRepository, times(1)).getAll();
    }

    @Test
    public void testClientDelete(){
        clientService.deleteClient(actualClient.getId());

        verify(clientRepository,times(1)).delete(actualClient.getId());
    }
    //endregion
}
