package com.desafio.starwars.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.desafio.starwars.entity.Planet;
import com.desafio.starwars.exception.CustomException;

@Component
public interface PlanetService {
	List<Planet> getPlanets(int page, String name);

	Optional<Planet> getPlanetById(long id);

	Planet insert(Planet planet);

	void delete(long id) throws CustomException;
}
