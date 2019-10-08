package com.desafio.starwars.controller;

import com.desafio.starwars.entity.Planet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PlanetControllerTest {
    private final RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    private int localPort;

    private String baseUrl;

    @Before
    public void setUp() {
        baseUrl = "http://localhost:{port}/starwars/planets".replace("{port}", String.valueOf(localPort));
    }

    @Test(expected = RestClientException.class)
    public void getPlanets() {
        restTemplate.getForEntity(baseUrl, Planet[].class);
    }

    @Test(expected = RestClientException.class)
    public void getPlanetsByName() {
        restTemplate.getForEntity(baseUrl, Planet[].class);
    }

    @Test(expected = RestClientException.class)
    public void getPlanetById() {
        restTemplate.getForEntity(baseUrl, Planet.class);
    }

    @Test(expected = RestClientException.class)
    public void addPlanet() {
        final String json = "{}";
        restTemplate.postForEntity(baseUrl, json, Planet.class);
    }

    @Test(expected = RestClientException.class)
    public void removePlanet() {
        restTemplate.delete(baseUrl);
    }
}