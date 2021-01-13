package com.maids.cc.store.management.backend.controller;

import com.maids.cc.store.management.backend.dto.request.ClientRequestDto;
import com.maids.cc.store.management.backend.dto.response.ClientResponseDto;
import com.maids.cc.store.management.backend.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController    // This means that this class is a Controller
@RequestMapping("/client")
@Api(value = "Client Management end-points", description = "Main CRUD end-points into Client")
public class ClientController {

    //region Properties
    @Autowired
    ClientService clientService;
    //endregion

    //region Get By Id
    @ApiOperation(value = "retrieve specific client from the database", response = ClientResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the client"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "{Id}")
    public ResponseEntity<ClientResponseDto> get(@PathVariable UUID Id) {

        ResponseEntity<ClientResponseDto> result = clientService.getClient(Id);

        return result;
    }
    //endregion

    //region Get All
    @ApiOperation(value = "retrieve all the clients from the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved clients list"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> getAll() {

        ResponseEntity<List<ClientResponseDto>> result = clientService.getAllClients();

        return result;
    }
    //endregion

    //region Post
    @ApiOperation(value = "add a new client to the database", response = ClientResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "client has been added successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    public ResponseEntity<ClientResponseDto> addClient(@RequestBody ClientRequestDto dto) {

        ResponseEntity<ClientResponseDto> result = clientService.addClient(dto);

        return result;
    }
    //endregion

    //region Put
    @ApiOperation(value = "update a client to the database", response = ClientResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "client has been updated successfully!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "{Id}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable UUID Id, @RequestBody ClientRequestDto dto) {

        ResponseEntity<ClientResponseDto> result = clientService.updateClient(Id, dto);

        return result;
    }
    //endregion

    //region Delete
    @ApiOperation(value = "Delete specific client from the database", response = boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "client has been removed successfully!"),
            @ApiResponse(code = 204, message = "client already had been removed from the database!"),
            @ApiResponse(code = 401, message = "Unauthorized request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "{Id}")
    public boolean deleteClient(@PathVariable UUID Id) {

        boolean result = clientService.deleteClient(Id);

        return result;
    }
    //endregion
}
