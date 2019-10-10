package com.desafio.starwars.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "planets")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet implements Serializable {

	public Planet(final Long id, final String name, final String climate, final String terrain) {
		this.id = id;
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}

	private static final long serialVersionUID = 5416080018525612681L;

	@Id
	private Long id;// TODO auto generate

	@NotBlank
	@JsonProperty(required = true)
	private String name;
	@NotBlank
	@JsonProperty(required = true)
	private String climate;
	@NotBlank
	@JsonProperty(required = true)
	private String terrain;

	@Transient
	@JsonIgnoreProperties(value = { "amountMoviesAppearance" }, allowGetters = true)
	private int amountMoviesAppearance;
}