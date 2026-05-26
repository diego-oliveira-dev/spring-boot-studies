package com.diego.projetos.springboot_studies.repository;

import com.diego.projetos.springboot_studies.domain.Anime;

import java.util.List;

public interface AnimeRepository {
//    private final AnimeRepository animeRepository;
    List<Anime> listAll();
}
