package com.desafio.starwars.service;

import com.desafio.starwars.entity.Planet;
import com.desafio.starwars.exception.CustomException;
import com.desafio.starwars.repository.PlanetRepository;
import com.desafio.starwars.service.impl.PlanetServiceImpl;
import com.desafio.starwars.service.impl.SwApiServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlanetServiceTest {

    private final Planet planet = new Planet(42L, "testName", "climateTest", "terrainTest");

    @InjectMocks
    private PlanetServiceImpl planetService;

    @Mock
    private PlanetRepository repository;

    @Mock
    private SwApiServiceImpl swApiService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getPlanets() {
        when(repository.findPlanetByName(planet.getName())).thenReturn(List.of(planet));
        final List<Planet> planets = planetService.getPlanets(planet.getName());

        assertNotNull(planets);
    }

    @Test
    public void getPlanetById() throws CustomException {
        when(repository.findById(planet.getId())).thenReturn(Optional.of(planet));
        final var planetReturned = planetService.getPlanetById(planet.getId());

        assertNotNull(planet);
        assertEquals(planet.getId(), planetReturned.getId());
    }

    @Test
    public void insert() {
        when(repository.save(planet)).thenReturn(planet);

        final Planet planetReturned = planetService.insert(planet);
        assertNotNull(planetReturned);
        assertEquals(planet, planetReturned);
    }

    @Test(expected = CustomException.class)
    public void deleteWithNoExistingId() throws CustomException {
        planetService.delete(planet.getId());
    }
}