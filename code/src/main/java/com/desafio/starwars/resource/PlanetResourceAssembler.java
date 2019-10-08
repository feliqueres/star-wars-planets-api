package com.desafio.starwars.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.desafio.starwars.controller.PlanetController;
import com.desafio.starwars.entity.Planet;

@Component
public class PlanetResourceAssembler implements ResourceAssembler<Planet, Resource<Planet>> {

	@Override
	public Resource<Planet> toResource(final Planet planet) {
		return new Resource<Planet>(planet,
				linkTo(methodOn(PlanetController.class).getPlanetById(planet.getId())).withSelfRel());
	}

}
