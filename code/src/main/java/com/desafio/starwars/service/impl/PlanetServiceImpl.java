package com.desafio.starwars.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.desafio.starwars.entity.Planet;
import com.desafio.starwars.exception.CustomException;
import com.desafio.starwars.repository.PlanetRepository;
import com.desafio.starwars.service.PlanetService;
import com.desafio.starwars.service.SwApiService;

@Service
public class PlanetServiceImpl implements PlanetService {
	@Autowired
	private PlanetRepository repository;
	@Autowired
	private SwApiService swApiService;

	@Override
	public List<Planet> getPlanets(final int page, final String name) {
		final List<Planet> planets;
		if (StringUtils.isNotBlank(name))
			planets = repository.findPlanetByName(name);
		else {
			planets = repository.findAll();
		}
		planets.forEach(
				planet -> planet.setAmountMoviesAppearance(swApiService.getPlanetsByName(planet.getName()).size()));
		return planets;
	}

	@Override
	public Optional<Planet> getPlanetById(final long id) {
		return repository.findById(id);
	}

	@Override
	public Planet insert(final Planet planet) {
		if (planet.getId() != null && planet.getId() > 0 && repository.existsById(planet.getId()))
			throw new DuplicateKeyException("ID already exists");
		return repository.save(planet);
	}

	@Override
	public void delete(final long id) throws CustomException {
		if (!repository.existsById(id))
			throw new CustomException("Item not found");
		repository.deleteById(id);
	}
}
