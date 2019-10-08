package com.desafio.starwars.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "internal.infos")
@Data
public class ConfigProperties {
	private String swapiPlanetsUri;
}
