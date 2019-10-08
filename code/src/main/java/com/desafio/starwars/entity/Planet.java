package com.desafio.starwars.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Document(collection = "planets")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet implements Serializable {
	private static final long serialVersionUID = 5416080018525612681L;

	@Id
	private Long id;

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
	private int amountMoviesAppearance;
}