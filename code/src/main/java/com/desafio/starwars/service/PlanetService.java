package com.desafio.starwars.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.desafio.starwars.entity.Planet;
import com.desafio.starwars.exception.CustomException;

@Component
public interface PlanetService {
    List<Planet> getPlanets(String name);

    Planet getPlanetById(long id) throws CustomException;

    Planet insert(Planet planet);

    void delete(long id) throws CustomException;
}
