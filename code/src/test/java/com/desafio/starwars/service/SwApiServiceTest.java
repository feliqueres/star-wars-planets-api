package com.desafio.starwars.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SwApiServiceTest {
    @Autowired
    private SwApiService swApiService;

    @Test
    public void getPlanetsByName() {
        assertNotNull(swApiService.getPlanetsByName("testName"));
    }

    @Test
    public void getAmountMoviesAppearance() {
        swApiService.getPlanetsByName("").stream().findAny().ifPresent(planet -> assertNotNull(planet.getFilms()));
    }

    @Test
    public void decodeParam() {
        final String decodedParam = swApiService.decodeParam("ç");
        assertTrue(StringUtils.isNotBlank(decodedParam));
        assertEquals("%C3%A7", decodedParam);
    }
}