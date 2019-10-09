package com.desafio.starwars.service.impl;

import com.desafio.starwars.entity.Planet;
import com.desafio.starwars.exception.CustomException;
import com.desafio.starwars.repository.PlanetRepository;
import com.desafio.starwars.service.PlanetService;
import com.desafio.starwars.service.SwApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetServiceImpl implements PlanetService {
    final private PlanetRepository repository;
    final private SwApiService swApiService;

    public PlanetServiceImpl(PlanetRepository repository, SwApiService swApiService) {
        this.repository = repository;
        this.swApiService = swApiService;
    }

    @Override
    public List<Planet> getPlanets(final String name) {
        final List<Planet> planets;
        if (StringUtils.isNotBlank(name))
            planets = repository.findPlanetByName(name);
        else {
            planets = repository.findAll();
        }
        for (Planet planet : planets) {
            planet.setAmountMoviesAppearance(swApiService.getAmountMoviesAppearance(planet.getName()));
        }
        return planets;
    }

    @Override
    public Planet getPlanetById(final long id) throws CustomException {
        return repository.findById(id).orElseThrow(() -> new CustomException("ID not found"));
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
