package ru.yandex.practicum.filmorate;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import ru.yandex.practicum.filmorate.model.User;

@SpringBootTest()
class UserControllerTests {
    WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080/users").build();

    @Test
    void emailValidation_ifNull_shouldBeStatus500() {
        User user = User.builder()
            .login("login")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        webTestClient.post()
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    void emailValidation_ifBlank_shouldBeStatus500() {
        User user = User.builder()
            .email("  ")
            .login("login")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        webTestClient.post()
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    void emailValidation_ifNoAt_shouldBeStatus500() {
        User user = User.builder()
            .email("mail.ru")
            .login("login")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        webTestClient.post()
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    void loginValidation_ifNull_shouldBeStatus500() {
        User user = User.builder()
            .email("mail@mail.ru")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        webTestClient.post()
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    void loginValidation_ifBlank_shouldBeStatus500() {
        User user = User.builder()
            .login("")
            .email("mail@mail.ru")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        webTestClient.post()
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    void loginValidation_ifContainsSpace_shouldBeStatus500() {
        User user = User.builder()
            .login("login with space")
            .email("mail@mail.ru")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        webTestClient.post()
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    void nameValidation_ifNull_shouldUseLogin() {
        User user = User.builder()
            .login("login")
            .email("mail@mail.ru")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        webTestClient.post()
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.name").isEqualTo(user.getLogin());
    }

    @Test
    void birthdayValidation_ifInFuture_shouldBeStatus500() {
        User user = User.builder()
            .login("login")
            .email("mail@mail.ru")
            .name("name")
            .birthday(LocalDate.of(2446, 8, 20))
            .build();

        webTestClient.post()
            .body(Mono.just(user), User.class)
            .exchange()
            .expectStatus().is5xxServerError();
    }
}
