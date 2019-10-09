package com.desafio.starwars.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Document(collection = "planets")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet implements Serializable {
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
    @JsonIgnoreProperties(value = {"amountMoviesAppearance"}, allowGetters = true)
    private int amountMoviesAppearance;
}