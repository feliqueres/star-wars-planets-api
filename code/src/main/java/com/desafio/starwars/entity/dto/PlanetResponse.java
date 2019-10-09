package com.desafio.starwars.entity.dto;

import com.desafio.starwars.entity.Planet;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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