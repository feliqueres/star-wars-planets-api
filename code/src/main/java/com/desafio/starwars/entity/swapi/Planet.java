package com.desafio.starwars.entity.swapi;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet implements Serializable {
	private static final long serialVersionUID = 618907469281379119L;

	public String name;
	public List<String> films = null;
}