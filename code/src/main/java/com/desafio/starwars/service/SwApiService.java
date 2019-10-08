package com.desafio.starwars.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Component;

import com.desafio.starwars.entity.swapi.Planet;

@Component
public interface SwApiService {
	List<Planet> getPlanetsByName(String name);

	default public String decodeParam(final String name) {
		return URLEncoder.encode(name, StandardCharsets.UTF_8);
	}
}