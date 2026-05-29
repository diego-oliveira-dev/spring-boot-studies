package com.diego.projetos.springboot_studies.service;

import com.diego.projetos.springboot_studies.domain.Anime;
import com.diego.projetos.springboot_studies.exception.BadRequestException;
import com.diego.projetos.springboot_studies.repository.AnimeRepository;
import com.diego.projetos.springboot_studies.requests.AnimePostRequestBody;
import com.diego.projetos.springboot_studies.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public List<Anime> listAll() {
        return animeRepository.findAll();
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Anime findByIdOrThrowBadRequestException(long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }

    public Anime save(AnimePostRequestBody animePostRequestBody) {
        // without using mapping framework (hands-on):
        // default return of 'save' is the updated object
        Anime anime = Anime.builder().name(animePostRequestBody.getName()).build();
        return animeRepository.save(anime);

        // using mapping framework (MapStruct):
        // return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }
    // RFC7231 -> HTTP protocol reference 4.2.1
    // Non-Idempotent Methods (POST, PATCH)
    // and Idempotent Methods(GET, PUT, DELETE)

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        // without mapping framework:
        Anime anime = Anime.builder().id(savedAnime.getId()).name(animePutRequestBody.getName()).build();
        animeRepository.save(anime); // by itself, does not check if the anime in fact exists

        // using mapping framework:
        // Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        // anime.setId(savedAnime.getId());
        // animeRepository.save(anime);
    }
}
