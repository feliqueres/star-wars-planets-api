package com.desafio.starwars.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.desafio.starwars.entity.swapi.Planet;
import com.desafio.starwars.entity.swapi.SwApiResponse;
import com.desafio.starwars.service.SwApiService;

@Service
public class SwApiServiceImpl implements SwApiService {

    private static final Logger LOG = LoggerFactory.getLogger(SwApiServiceImpl.class);

    @Override
    public List<Planet> getPlanetsByName(final String name) {
        final RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SwApiResponse> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity("http://swapi.co/planets/?search=".concat(name),
                    SwApiResponse.class);
            // TODO get url from applitation.properties
        } catch (final RestClientException e) {
            LOG.warn("Erro ao buscar informações no swapi.co: {}", e.getMessage());
        }
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            final Optional<SwApiResponse> objOptional = Optional.ofNullable(responseEntity.getBody());
            if (objOptional.isPresent())
                return objOptional.get().getPlanets();
        }
        return new ArrayList<Planet>();
    }
}