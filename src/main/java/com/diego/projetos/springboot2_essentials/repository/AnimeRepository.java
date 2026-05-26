package com.diego.projetos.springboot2_essentials.repository;

import com.diego.projetos.springboot2_essentials.domain.Anime;

import java.util.List;

public interface AnimeRepository {
//    private final AnimeRepository animeRepository;
    List<Anime> listAll();
}
