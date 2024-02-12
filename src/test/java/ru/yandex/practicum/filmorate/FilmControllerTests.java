package ru.yandex.practicum.filmorate;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import ru.yandex.practicum.filmorate.model.Film;

@SpringBootTest(classes = FilmorateApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
class FilmControllerTests {
    private WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080/films").build();

    @Test
    void nameValidation_ifNull_shouldBeStatus500() {
        Film film = Film.builder()
            .description("description")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(120)
            .build();

        webTestClient.post()
            .body(Mono.just(film), Film.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    void nameValidation_ifBlank_shouldBeStatus500() {
        Film film = Film.builder()
            .name("   ")
            .description("description")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(120)
            .build();

        webTestClient.post()
            .body(Mono.just(film), Film.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    void descriptionValidation_ifMoreThan200Symbols_shouldBeStatus500() {
        Film film = Film.builder()
            .name("name")
            .description("Very description more than 200 symbols. Very description more than 200 symbols. "
                + "Very description more than 200 symbols. Very description more than 200 symbols. "
                + "Very description more than 200 symbols...")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(120)
            .build();

        webTestClient.post()
            .body(Mono.just(film), Film.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    void releaseDateValidation_ifBefore_1895_12_28_shouldBeStatus500() {
        Film film = Film.builder()
            .name("name")
            .description("description")
            .releaseDate(LocalDate.of(1895, 12, 27))
            .duration(120)
            .build();

        webTestClient.post()
            .body(Mono.just(film), Film.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    void durationValidation_ifNegative_shouldBeStatus500() {
        Film film = Film.builder()
            .name("name")
            .description("description")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(-120)
            .build();

        webTestClient.post()
            .body(Mono.just(film), Film.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }
}
