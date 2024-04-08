package com.ibm.mateusmelo.rest.controller;

import com.ibm.mateusmelo.domain.entity.Client;
import com.ibm.mateusmelo.domain.repository.Clients;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/clients")
public class ClientController {
    private final Clients repository;

    public ClientController(Clients repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Client> findAll(Client filter) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Client> example = Example.of(filter, matcher);
        return repository.findAll(example);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(OK)
    @Operation(
            description = "Get all client information by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer was found"),
                    @ApiResponse(responseCode = "400", description = "Client not found given typed ID")
            }

    )
    public Client getById(
            @Parameter(description = "Client's ID")
            @PathVariable Integer id
    ) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                NOT_FOUND,
                                "Client not found"
                        )
                );
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(
            description = "Save a new client",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client was created successfully"),
                    @ApiResponse(responseCode = "400", description = "Occurred an validation error. Check entry data")
            }
    )
    public Client create(@RequestBody @Valid Client client) {
        return repository.save(client);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(NO_CONTENT)
    public Client update(@PathVariable Integer id, @RequestBody @Valid Client newClient) {
        return repository.findById(id)
                .map(
                        client -> {
                            newClient.setId(client.getId());
                            repository.save(newClient);
                            return newClient;
                }).orElseThrow(
                        () -> new ResponseStatusException(
                                NOT_FOUND,
                                "Client not found"
                        )
                );
    }

    @DeleteMapping("/{id}")
    public Client delete(@PathVariable Integer id) {
        return repository.findById(id)
                .map(
                        client -> {
                            repository.deleteById(id);
                            return client;
                }).orElseThrow(
                        () -> new ResponseStatusException(
                                NOT_FOUND,
                                "Client not found"
                        )
                );
        }
}
