package com.desafio.starwars.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.desafio.starwars.entity.Planet;
import com.desafio.starwars.entity.dto.PlanetResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PlanetControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(PlanetControllerTest.class);

	private final RestTemplate restTemplate = new RestTemplate();

	@LocalServerPort
	private int localPort;

	private String baseUrl;

	private Planet planet;
	private URI insertedPlanetlocation;

	@Before
	public void setUp() {
		baseUrl = "http://localhost:{port}/starwars/planets/".replace("{port}", String.valueOf(localPort));

		planet = new Planet();
		planet.setName("nameTest");
		planet.setClimate("climateTest");
		planet.setTerrain("terrainTest");
		planet.setId(42L);
	}

	@Test
	public void addPlanet() {
		try {
			final ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, planet, String.class);
			assertEquals(HttpStatus.CREATED, response.getStatusCode());

			insertedPlanetlocation = response.getHeaders().getLocation();
			assertNotNull(insertedPlanetlocation);

			getPlanetById();
			getPlanetsTest();
			getPlanetsByName();
			addPlanetConflictTest();
			removePlanet();
		} catch (final Exception e) {
			LOG.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	public void getPlanetById() {
		try {
			final ResponseEntity<Planet> responseEntity = restTemplate.getForEntity(insertedPlanetlocation,
					Planet.class);

			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

			final Planet responsePlanet = responseEntity.getBody();
			assertNotNull(responsePlanet);
			assertEquals(planet.getId(), responsePlanet.getId());
			assertEquals(planet.getName(), responsePlanet.getName());
			assertEquals(planet.getClimate(), responsePlanet.getClimate());
			assertEquals(planet.getTerrain(), responsePlanet.getTerrain());
		} catch (final HttpClientErrorException e) {
			fail(e.getMessage());
		}
	}

	public void getPlanetsTest() {
		try {
			restTemplate.exchange(baseUrl, HttpMethod.GET, null, PlanetResponse.class);
			final ResponseEntity<PlanetResponse> responseEntity = restTemplate.getForEntity(baseUrl,
					PlanetResponse.class);

			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			final var planetResponse = responseEntity.getBody();
			assertNotNull(planetResponse);
			assertNotNull(planetResponse.getPlanets());
			assertTrue(planetResponse.getPlanets().stream().map(Planet::getName)
					.anyMatch(planet.getName()::equalsIgnoreCase));
		} catch (final Exception e) {
			fail(e.getMessage());
		}
	}

	public void getPlanetsByName() {
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put("name", planet.getName());
		try {
			final ResponseEntity<PlanetResponse> responseEntity = restTemplate.getForEntity(baseUrl,
					PlanetResponse.class, queryParams);

			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			final var planetResponse = responseEntity.getBody();
			assertNotNull(planetResponse);
			assertNotNull(planetResponse.getPlanets());
			assertTrue(planetResponse.getPlanets().stream().map(Planet::getName)
					.anyMatch(planet.getName()::equalsIgnoreCase));
		} catch (final Exception e) {
			fail(e.getMessage());
		}
	}

	public void addPlanetConflictTest() {
		try {
			restTemplate.getForEntity(insertedPlanetlocation, Planet.class);
		} catch (final RestClientException e) {
			final Throwable cause = e.getMostSpecificCause();
			assertTrue(cause instanceof HttpClientErrorException
					&& HttpStatus.CONFLICT.equals(((HttpClientErrorException) cause).getStatusCode()));
		}
	}

	public void removePlanet() {
		try {
			restTemplate.delete(insertedPlanetlocation);
		} catch (final HttpClientErrorException e) {
			fail(e.getMessage());
		}
		try {
			restTemplate.getForEntity(insertedPlanetlocation, Planet.class);
		} catch (final RestClientException e) {
			assertTrue(e.getMostSpecificCause() instanceof HttpClientErrorException
					&& HttpStatus.NOT_FOUND.equals(((HttpClientErrorException) e).getStatusCode()));
		}
	}

	@Test
	public void deleteBadRequestTest() {
		try {
			restTemplate.delete(baseUrl.concat("-1"));
		} catch (final RestClientException e) {
			final Throwable cause = e.getMostSpecificCause();
			assertTrue(cause instanceof HttpClientErrorException
					&& HttpStatus.NOT_FOUND.equals(((HttpClientErrorException) cause).getStatusCode()));
		}
	}

	@Test
	public void getByIdBadRequestTest() {
		try {
			restTemplate.getForEntity(baseUrl.concat("-1"), Planet.class);
		} catch (final RestClientException e) {
			final Throwable cause = e.getMostSpecificCause();
			assertTrue(cause instanceof HttpClientErrorException
					&& HttpStatus.NOT_FOUND.equals(((HttpClientErrorException) cause).getStatusCode()));
		}
	}
}