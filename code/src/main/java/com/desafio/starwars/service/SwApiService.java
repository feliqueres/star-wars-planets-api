package com.desafio.starwars.service;

import com.desafio.starwars.entity.swapi.Planet;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public interface SwApiService {
    List<Planet> getPlanetsByName(String name);

    int getAmountMoviesAppearance(String name);

    default String decodeParam(final String name) {
        return URLEncoder.encode(name, StandardCharsets.UTF_8);
    }
}