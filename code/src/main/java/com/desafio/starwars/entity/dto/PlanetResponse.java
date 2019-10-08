package com.desafio.starwars.entity.dto;

import java.io.Serializable;
import java.util.List;

import com.desafio.starwars.entity.Planet;

import lombok.Data;

@Data
public class PlanetResponse implements Serializable {
	private static final long serialVersionUID = -6278930965643900570L;

	private int size;
	private String next;
	private String previous;
	private List<Planet> planets;

	public PlanetResponse(final List<Planet> planets, final int size) {
		this.planets = planets;
		this.size = size;
	}
}