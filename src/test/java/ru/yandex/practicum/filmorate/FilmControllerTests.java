package ru.yandex.practicum.filmorate;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.model.Film;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest()
class FilmControllerTests {
    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    void nameValidation_ifNull_shouldNotPassValidation() {
        Film film = Film.builder()
            .description("description")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(120)
            .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация названия фильма, если названия нет"
        );
    }

    @Test
    void nameValidation_ifBlank_shouldNotPassValidation() {
        Film film = Film.builder()
            .name("   ")
            .description("description")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(120)
            .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация названия фильма, если оно состоит из пробелов"
        );
    }

    @Test
    void descriptionValidation_ifMoreThan200Symbols_shouldNotPassValidation() {
        Film film = Film.builder()
            .name("name")
            .description("Very description more than 200 symbols. Very description more than 200 symbols. "
                + "Very description more than 200 symbols. Very description more than 200 symbols. "
                + "Very description more than 200 symbols...")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(120)
            .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация описания фильма, если оно длиннее 200 символов"
        );
    }

    @Test
    void releaseDateValidation_ifBefore_1895_12_28_shouldNotPassValidation() {
        Film film = Film.builder()
            .name("name")
            .description("description")
            .releaseDate(LocalDate.of(1895, 12, 27))
            .duration(120)
            .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация даты релиза фильма, если она раньше 27.12.1895"
        );
    }

    @Test
    void durationValidation_ifNegative_shouldNotPassValidation() {
        Film film = Film.builder()
            .name("name")
            .description("description")
            .releaseDate(LocalDate.of(1990, 1, 1))
            .duration(-120)
            .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация продолжительности фильма, если она отрицательная"
        );
    }
}
