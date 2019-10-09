package com.desafio.starwars.controller;

import com.desafio.starwars.entity.Planet;
import com.desafio.starwars.entity.dto.PlanetResponse;
import com.desafio.starwars.exception.CustomException;
import com.desafio.starwars.resource.PlanetResourceAssembler;
import com.desafio.starwars.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetService planetService;
    private final PlanetResourceAssembler resourceAssembler;

    @Autowired
    public PlanetController(final PlanetService planetService, final PlanetResourceAssembler resourceAssembler) {
        this.planetService = planetService;
        this.resourceAssembler = resourceAssembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PlanetResponse> getPlanets(@RequestParam(value = "name", required = false) final String name) {
        // TODO pagination logic with URLs {previous/next}

        final List<Planet> planets = planetService.getPlanets(name);
        final PlanetResponse planetResponse = new PlanetResponse(planets, planets.size());
        return ResponseEntity.ok(planetResponse);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Planet> getPlanetById(@PathVariable("id") final long id) {
        try {
            return ResponseEntity.ok(planetService.getPlanetById(id));
        } catch (CustomException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Planet> addPlanet(@RequestBody final Planet planet) throws URISyntaxException {
        // TODO erro amigável quando não vier obj esperado
        Planet insertedPlanet;
        try {
            insertedPlanet = planetService.insert(planet);
        } catch (final DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        final URI uri = new URI(resourceAssembler.toResource(insertedPlanet).getId().expand().getHref());
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Planet> removePlanet(@PathVariable("id") final long id) {
        try {
            planetService.delete(id);
        } catch (final CustomException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
}
