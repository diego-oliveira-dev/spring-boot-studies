package com.diego.projetos.springboot_studies.client;

import com.diego.projetos.springboot_studies.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity(
                "http://localhost:8080/animes/{id}", Anime.class, 8);
        log.info(entity);

        Anime object = new RestTemplate().getForObject(
                "http://localhost:8080/animes/{id}", Anime.class, 8);
        log.info(object); // returns only the anime, without the data present on ResponseEntity

        // if you want to get a list of objects, you can use:

        Anime[] animes = new RestTemplate().getForObject(
                "http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));
        // but this way can be very troublesome since you're handling Arrays
        // casting to List can be very problematic too

        // so an alternative would be:

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange(
                "http://localhost:8080/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }); // -> super type token
        log.info(exchange.getBody());
        // even though it's more verbose, it ensures that you're working with a List<T>
        // instead of an Array
    }
}
