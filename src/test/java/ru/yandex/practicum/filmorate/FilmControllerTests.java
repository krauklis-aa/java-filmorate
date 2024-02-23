package ru.yandex.practicum.filmorate;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest()
class FilmControllerTests {
    FilmController filmController = new FilmController();

    @Test
    void nameValidation_ifNull_shouldThrowValidationException() {
        Film film = Film.builder()
            .description("description")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(120)
            .build();

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> filmController.validate(film),
            "Не прошла валидация названия фильма, если названия нет"
        );

        assertEquals("Название фильма не может быть пустым", e.getMessage());
    }

    @Test
    void nameValidation_ifBlank_shouldThrowValidationException() {
        Film film = Film.builder()
            .name("   ")
            .description("description")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(120)
            .build();

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> filmController.validate(film),
            "Не прошла валидация названия фильма, если оно состоит из пробелов"
        );

        assertEquals("Название фильма не может быть пустым", e.getMessage());
    }

    @Test
    void descriptionValidation_ifMoreThan200Symbols_shouldThrowValidationException() {
        Film film = Film.builder()
            .name("name")
            .description("Very description more than 200 symbols. Very description more than 200 symbols. "
                + "Very description more than 200 symbols. Very description more than 200 symbols. "
                + "Very description more than 200 symbols...")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(120)
            .build();

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> filmController.validate(film),
            "Не прошла валидация описания фильма, если оно длиннее 200 символов"
        );

        assertEquals("Описание должно быть менее 200 символов", e.getMessage());
    }

    @Test
    void releaseDateValidation_ifBefore_1895_12_28_shouldThrowValidationException() {
        Film film = Film.builder()
            .name("name")
            .description("description")
            .releaseDate(LocalDate.of(1895, 12, 27))
            .duration(120)
            .build();

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> filmController.validate(film),
            "Не прошла валидация даты релиза фильма, если она раньше 27.12.1895"
        );

        assertEquals("Дата релиза должна быть позже 28 декабря 1895 года", e.getMessage());
    }

    @Test
    void durationValidation_ifNegative_shouldThrowValidationException() {
        Film film = Film.builder()
            .name("name")
            .description("description")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(-120)
            .build();

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> filmController.validate(film),
            "Не прошла валидация продолжительности фильма, если она отрицательная"
        );

        assertEquals("Продолжительность фильма должна быть положительной", e.getMessage());
    }
}
