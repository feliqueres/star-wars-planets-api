package com.desafio.starwars.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.desafio.starwars.entity.Planet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetRepositoryTest {
	@Autowired
	private PlanetRepository repository;

	private final Planet planet = new Planet(666L, "nameTest", "climateTest", "terrainTest");

	@Test
	public void tests() {
		// doesn't exists
		assertTrue(repository.findById(planet.getId()).isEmpty());

		// inserts
		assertEquals(planet, repository.save(planet));
		// exists
		assertTrue(repository.findById(planet.getId()).isPresent());
		// finds by name
		assertTrue(repository.findPlanetByName(planet.getName()).stream().map(Planet::getName)
				.anyMatch(planet.getName()::equals));

		// not emppty
		assertTrue(!repository.findAll().isEmpty());

		repository.delete(planet);
		// confirms deletion
		assertTrue(repository.findById(planet.getId()).isEmpty());
	}
}