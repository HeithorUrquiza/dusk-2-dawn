package me.hecth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.hecth.domain.models.Game;
import me.hecth.services.GameServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("games")
@Tag(name = "Games Controller", description = "RESTful API for managing games.")
public class GamesController {
    @Autowired
    private GameServices gameServices;

    @GetMapping
    @Operation(summary = "Get all games", description = "Retrieve a list of all registred games")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<Game>> findAll() {
        List<Game> games = gameServices.findAll();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{title}")
    @Operation(summary = "Get a game by Title", description = "Retrieve a specific game based on its Title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    public ResponseEntity<Game> findByTitle(@PathVariable String title) {
        Game game = gameServices.findByTitle(title);
        return ResponseEntity.ok(game);
    }

    @PostMapping
    @Operation(summary = "Registry a new game",
            description = "Registry a new game and return the registered game's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game registered successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid game data provided")
    })
    public ResponseEntity<Game> create(@RequestBody Game game) {
        Game gameRegistered = gameServices.create(game);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{title}")
                .buildAndExpand(gameRegistered.getTitle())
                .toUri();
        return ResponseEntity.created(location).body(gameRegistered);
    }

    @PutMapping("/{title}")
    @Operation(summary = "Update a game", description = "Update the data of an existing game based on its Title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game updated successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found"),
            @ApiResponse(responseCode = "422", description = "Invalid game data provided")
    })
    public ResponseEntity<Game> update(@PathVariable String title, @RequestBody Game game) {
        Game gameUpdated = gameServices.update(title, game);
        return ResponseEntity.ok(gameUpdated);
    }

    @DeleteMapping("/{title}")
    @Operation(summary = "Delete a game", description = "Delete an existing game based on its Title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Game deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    public ResponseEntity<Void> delete(@PathVariable String title) {
        gameServices.delete(title);
        return ResponseEntity.noContent().build();
    }
}
