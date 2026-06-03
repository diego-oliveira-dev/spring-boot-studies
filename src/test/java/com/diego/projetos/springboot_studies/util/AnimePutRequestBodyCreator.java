package com.diego.projetos.springboot_studies.util;

import com.diego.projetos.springboot_studies.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {

    public static AnimePutRequestBody createAnimePutRequestBody() {
        return AnimePutRequestBody.builder()
                .id(AnimeCreator.createValidAnime().getId())
                .name(AnimeCreator.createValidAnime().getName())
                .build();
    }
}
