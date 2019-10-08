package com.desafio.starwars.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.desafio.starwars.entity.Planet;

public interface PlanetRepository extends MongoRepository<Planet, Long> {
	List<Planet> findPlanetByName(String name);
}