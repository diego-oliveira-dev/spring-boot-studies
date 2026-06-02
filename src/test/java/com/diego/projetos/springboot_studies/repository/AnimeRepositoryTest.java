package com.diego.projetos.springboot_studies.repository;

import com.diego.projetos.springboot_studies.domain.Anime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // makes the test focus only on JPA components
    // tests annotated with @DataJpaTest are transactional and roll back at the end
    // they also use in-memory database (such as H2)
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {
    @Autowired // it's not a good practice to use @Autowired directly on fields but it isn't a issue for tests
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save: creates anime when successful")
    void save_PersistAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime savedAnime = this.animeRepository.save(animeToBeSaved);
        Assertions.assertNotNull(savedAnime);
        Assertions.assertNotNull(savedAnime.getId());
        Assertions.assertEquals(savedAnime, animeToBeSaved);
    }

    private Anime createAnime() {
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
}