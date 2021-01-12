package com.maids.cc.store.management.backend.extension;

import com.maids.cc.store.management.backend.dto.request.ClientRequestDto;
import com.maids.cc.store.management.backend.dto.response.ClientResponseDto;
import com.maids.cc.store.management.backend.entity.Client;

public class ClientExtension {

    public static Client toClientEntity(ClientRequestDto clientRequestDto){

        Client client = new Client();

        client.setFirstName(clientRequestDto.firstName);
        client.setLastName(clientRequestDto.lastName);
        client.setMobileNumber(clientRequestDto.mobileNumber);

        return client;
    }

    public static ClientResponseDto toClientDto(Client client){

        ClientResponseDto clientResponseDto = new ClientResponseDto();

        clientResponseDto.id = client.getId();
        clientResponseDto.firstName = client.getFirstName();
        clientResponseDto.lastName = client.getLastName();
        clientResponseDto.mobileNumber = client.getMobileNumber();

        return clientResponseDto;
    }
}
