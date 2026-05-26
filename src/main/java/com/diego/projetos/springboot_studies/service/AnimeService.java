package com.diego.projetos.springboot_studies.service;

import com.diego.projetos.springboot_studies.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AnimeService {
    private static List<Anime> animes;
    static {
        animes = new ArrayList<>(List.of(
                new Anime(1L, "Jujutsu Kaisen"),
                new Anime(2L, "JoJo"),
                new Anime(3L, "HxH")
        ));
    }

    public List<Anime> listAll() {
        return animes;
    }

    public Anime findById(long id) {
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(4, 25));
        animes.add(anime);
        return anime;
    }

    public void delete(long id) {
        animes.remove(findById(id));
    }
    // RFC7231 -> HTTP protocol reference
    // 4.2.1: Non-Idempotent Methods (POST, PATCH)
    // and Idempotent Methods(GET, PUT, DELETE)

    public void replace(Anime anime) { 
        delete(anime.getId());
        animes.add(anime);
    }
}
