package me.hecth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.hecth.domain.models.Developer;
import me.hecth.services.DeveloperServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/developers")
@Tag(name = "Developers Controller", description = "RESTful API for managing developers.")
public class DevelopersController {
    @Autowired
    private DeveloperServices developerServices;

    @GetMapping
    @Operation(summary = "Get all developers", description = "Retrieve a list of all registred developers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<Developer>> findAll() {
        List<Developer> devs = developerServices.findAll();
        return ResponseEntity.ok(devs);
    }

    @GetMapping("/{cpf}")
    @Operation(summary = "Get a developer by CPF", description = "Retrieve a specific user based on its CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Developer not found")
    })
    public ResponseEntity<Developer> findByCPF(@PathVariable String cpf) {
        Developer dev = developerServices.findByCPF(cpf);
        return ResponseEntity.ok(dev);
    }

    @PostMapping
    @Operation(summary = "Create a new developer",
            description = "Create a new developer and return the created dev's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Developer created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid developer data provided")
    })
    public ResponseEntity<Developer> create(@RequestBody Developer dev) {
        Developer devCreated = developerServices.create(dev);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{cpf}")
                .buildAndExpand(devCreated.getCpf())
                .toUri();
        return ResponseEntity.created(location).body(devCreated);
    }

    @PutMapping("/{cpf}")
    @Operation(summary = "Update a developer", description = "Update the data of an existing developer based on its CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer updated successfully"),
            @ApiResponse(responseCode = "404", description = "Developer not found"),
            @ApiResponse(responseCode = "422", description = "Invalid developer data provided")
    })
    public ResponseEntity<Developer> update(@PathVariable String cpf, @RequestBody Developer dev) {
        Developer devUpdated = developerServices.update(cpf, dev);
        return ResponseEntity.ok(devUpdated);
    }

    @DeleteMapping("/{cpf}")
    @Operation(summary = "Delete a developer", description = "Delete an existing developer based on its CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> delete(@PathVariable String cpf) {
        developerServices.delete(cpf);
        return ResponseEntity.noContent().build();
    }
}
