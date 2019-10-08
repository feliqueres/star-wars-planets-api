package com.desafio.starwars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarWarsApplication {
    // TODO keep info to not exceed requests
    public static void main(final String[] args) {
        SpringApplication.run(StarWarsApplication.class, args);
    }
}