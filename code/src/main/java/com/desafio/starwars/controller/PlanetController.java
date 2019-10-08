package com.desafio.starwars.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.starwars.entity.Planet;
import com.desafio.starwars.entity.dto.PlanetResponse;
import com.desafio.starwars.exception.CustomException;
import com.desafio.starwars.resource.PlanetResourceAssembler;
import com.desafio.starwars.service.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {

	private static final Logger LOG = LoggerFactory.getLogger(PlanetController.class);

	private final PlanetService planetService;
	private final PlanetResourceAssembler resourceAssembler;

	@Autowired
	public PlanetController(final PlanetService planetService, final PlanetResourceAssembler resourceAssembler) {
		this.planetService = planetService;
		this.resourceAssembler = resourceAssembler;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<PlanetResponse> getPlanets(@RequestParam(value = "name", required = false) final String name,
			@RequestParam(value = "page", required = false, defaultValue = "0") final String page) {
		// TODO pagination logic with URLs {previous/next}

		final List<Planet> planets = planetService.getPlanets(Integer.parseInt(page), name);
		final PlanetResponse planetResponse = new PlanetResponse(planets, planets.size());
		return ResponseEntity.ok(planetResponse);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Planet> getPlanetById(@PathVariable("id") final long id) {
		final Optional<Planet> planetById = planetService.getPlanetById(id);
		// TODO friendly response when bad request ->
		// https://www.toptal.com/java/spring-boot-rest-api-error-handling
		if (planetById.isPresent())
			return ResponseEntity.ok(planetById.get());
		else
			return ResponseEntity.notFound().build();
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
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
			LOG.warn(e.getMessage());
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}
}
