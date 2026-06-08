package com.diego.projetos.springboot_studies.integration;

import com.diego.projetos.springboot_studies.domain.Anime;
import com.diego.projetos.springboot_studies.domain.ProjectUser;
import com.diego.projetos.springboot_studies.repository.AnimeRepository;
import com.diego.projetos.springboot_studies.repository.ProjectUserRepository;
import com.diego.projetos.springboot_studies.requests.AnimePostRequestBody;
import com.diego.projetos.springboot_studies.util.AnimeCreator;
import com.diego.projetos.springboot_studies.util.AnimePostRequestBodyCreator;
import com.diego.projetos.springboot_studies.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase // contrary to @DataJpaTest, it doesn't do roll back after each method
// to work around it, we can use:
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// this annotation states that each test is considered dirty after execution
// that way Spring just deletes/drops the entire database after each one of them
public class AnimeControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser") // @Qualifier basically identifies which Bean to use
    private TestRestTemplate testRestTemplateRoleUser;
    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;
    //    @LocalServerPort
//    private int port;
//    you could use this method to get the port manually and save it in a variable
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private ProjectUserRepository projectUserRepository;

    private ProjectUser createUser() {
        return ProjectUser.builder()
                .name("Common User")
                .username("user")
                .password("$2a$12$6pCYErvjjh8a0ISkAM10UuUrddETi787EcseOZ.c7lPtOKkYK4Ytq") // user123
                .authorities("ROLE_USER")
                .build();
    }

    private ProjectUser createAdmin() {
        return ProjectUser.builder()
                .name("Diego Oliveira")
                .username("diego")
                .password("$2a$10$hQGrlIN94hqwshp7T83Sm.RsCPR6McvKkAh6eYJSybHLFj0OCyOee") // diego123
                .authorities("ROLE_ADMIN, ROLE_USER")
                .build();
    }
    // had to create these factory methods because using just static fields for every entity makes Hibernate work
    // in a strange way: basically it tries to UPDATE the object in the database, but since I'm using @DirtiesContext,
    // the row where the entity was doesn't exist anymore, so you get an exception. These methods solve the problem
    // because it basically generates copies of the entity every time it's called.

    @TestConfiguration
    @Lazy // it just generates the bean when someone needs it, and not when the application starts (default behaviour,
    // also called eager initialization)
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("user", "user123");
                    // every request made by this RestTemplate with the given credentials will be authorized
                    // it doesn't create the user though
            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("diego", "diego123");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("list returns list of animes inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        projectUserRepository.save(createUser());

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplateRoleUser.exchange("/animes", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of animes when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        projectUserRepository.save(createUser());

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        List<Anime> animeList = testRestTemplateRoleUser.exchange("/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animeList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animeList.getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        projectUserRepository.save(createUser());

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        long expectedId = savedAnime.getId();

        Anime anime = testRestTemplateRoleUser.getForObject("/animes/{id}", Anime.class, expectedId);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns anime list with similar names when successful")
    void findByName_ReturnsAnimeListWithSimilarNames_WhenSuccessful() {
        projectUserRepository.save(createUser());

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();
        String url = String.format("/animes/find?name=%s", expectedName);

        List<Anime> animeList = testRestTemplateRoleUser.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animeList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animeList.getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns empty list when anime is not found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        projectUserRepository.save(createUser());

        List<Anime> animeList = testRestTemplateRoleUser.exchange("/animes/find?name=ajuhygasuhas", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animeList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        projectUserRepository.save(createUser());

        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> animeResponseEntity = testRestTemplateRoleUser.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {
        projectUserRepository.save(createUser());

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        savedAnime.setName("new name");

        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/animes",
                HttpMethod.PUT,
                new HttpEntity<>(savedAnime),
                Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT  );
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        projectUserRepository.save(createAdmin());

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleAdmin.exchange("/animes/admin/{id}",
                HttpMethod.DELETE, null, Void.class, savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete returns 403 when user is not admin")
    void delete_Returns403_WhenUserIsNotAdmin() {
        projectUserRepository.save(createUser());

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/animes/admin/{id}",
                HttpMethod.DELETE, null, Void.class, savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
