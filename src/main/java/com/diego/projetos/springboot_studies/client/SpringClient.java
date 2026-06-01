package com.diego.projetos.springboot_studies.client;

import com.diego.projetos.springboot_studies.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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

        // RestTemplate GET method
        Anime[] animes = new RestTemplate().getForObject(
                "http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));
        // this way can be very troublesome since you're handling Arrays
        // casting to List can be very problematic too

        // so an alternative would be:

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange(
                "http://localhost:8080/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }); // -> super type token
        log.info(exchange.getBody());
        // even though it's more verbose, it ensures that you're working with a List<T>
        // instead of an Array

        // RestTemplate POST method
        Anime firstAnimeToBePosted = Anime.builder().name("Mushoku Tensei").build();
        Anime savedFirstAnime = new RestTemplate().postForObject("http://localhost:8080/animes",
                firstAnimeToBePosted, Anime.class);
        log.info("Posted first anime: {}", savedFirstAnime);

        // this version uses exchange method
        Anime secondAnimeToBePosted = Anime.builder().name("Grand Blue").build();
        ResponseEntity<Anime> savedSecondAnime = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.POST,
                // exchange can stand out because it can send HTTP headers inside HttpEntity, as showed below
                new HttpEntity<>(secondAnimeToBePosted, createJsonHeader() ),
                Anime.class);
        log.info("Posted second anime: {}", savedSecondAnime);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
