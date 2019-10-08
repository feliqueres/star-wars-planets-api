package com.desafio.starwars.entity.swapi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwApiResponse {

	public Integer count;
	public String next;
	public Object previous;
	@JsonProperty("results")
	public List<Planet> planets;
}
