package com.maids.cc.store.management.backend.service;

import com.maids.cc.store.management.backend.dto.request.ClientRequestDto;
import com.maids.cc.store.management.backend.dto.response.ClientResponseDto;
import com.maids.cc.store.management.backend.entity.Client;
import com.maids.cc.store.management.backend.extension.ClientExtension;
import com.maids.cc.store.management.backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public ResponseEntity<ClientResponseDto> getClient(UUID id){

        try {
            Client client = clientRepository.get(id);

            if(client == null){
                return new ResponseEntity<>(null, HttpStatus.FAILED_DEPENDENCY);
            }

            ClientResponseDto dto = ClientExtension.toClientDto(client);

            return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<ClientResponseDto>> getAllClients(){
        try {
            List<Client> clients = clientRepository.getAll();

            List<ClientResponseDto> clientDtos = clients.stream()
                    .map(ClientExtension::toClientDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(clientDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<ClientResponseDto> addClient(ClientRequestDto dto){
        try {
            Client client = ClientExtension.toClientEntity(dto);
            Client addedClient = clientRepository.save(client);

            ClientResponseDto responseDto = ClientExtension.toClientDto(addedClient);

            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<ClientResponseDto> updateClient(UUID id, ClientRequestDto dto){

        try {
            Client client = clientRepository.get(id);

            if(client == null){
                return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
            }

            client.setFirstName(dto.firstName);
            client.setLastName(dto.lastName);
            client.setMobileNumber(dto.mobileNumber);

            Client addedClient = clientRepository.save(client);

            ClientResponseDto responseDto = ClientExtension.toClientDto(addedClient);

            return new ResponseEntity<>(responseDto,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public boolean deleteClient(UUID id){
        Client client = clientRepository.get(id);

        if(client == null){
            return false;
        }

        clientRepository.delete(id);

        return true;
    }
}
