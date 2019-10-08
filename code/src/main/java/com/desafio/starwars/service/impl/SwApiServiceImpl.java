package com.desafio.starwars.service.impl;

import com.desafio.starwars.configuration.ConfigProperties;
import com.desafio.starwars.entity.swapi.Planet;
import com.desafio.starwars.entity.swapi.SwApiResponse;
import com.desafio.starwars.service.SwApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SwApiServiceImpl implements SwApiService {

    private static final Logger LOG = LoggerFactory.getLogger(SwApiServiceImpl.class);

    @Autowired
    private ConfigProperties configProperties;

    @Override
    public List<Planet> getPlanetsByName(final String name) {
        final RestTemplate restTemplate = new RestTemplate();
        try {
            var responseEntity = restTemplate.getForEntity(configProperties.getSwapiPlanetsUri().concat(decodeParam(name)),
                    SwApiResponse.class);
            if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                final Optional<SwApiResponse> objOptional = Optional.ofNullable(responseEntity.getBody());
                if (objOptional.isPresent())
                    return objOptional.get().getPlanets();
            }
        } catch (final RestClientException e) {
            LOG.warn("Erro ao buscar informações no swapi.co: {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public int getAmountMoviesAppearance(String name) {
        final var planetList = getPlanetsByName(name);
        return planetList.stream().mapToInt(planet -> planet.getFilms().size()).sum();
    }
}