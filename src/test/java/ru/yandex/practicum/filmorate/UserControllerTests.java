package ru.yandex.practicum.filmorate;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest()
class UserControllerTests {
    UserController userController = new UserController();

    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    void emailValidation_ifNull_shouldThrowValidationException() {
        User user = User.builder()
            .login("login")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация емейла, если его нет"
        );
    }

    @Test
    void emailValidation_ifBlank_shouldThrowValidationException() {
        User user = User.builder()
            .email("  ")
            .login("login")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация емейла, если он состоит из пробелов"
        );
    }

    @Test
    void emailValidation_ifNoAt_shouldThrowValidationException() {
        User user = User.builder()
            .email("mail.ru")
            .login("login")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация емейла, если он не содержит @"
        );
    }

    @Test
    void loginValidation_ifNull_shouldThrowValidationException() {
        User user = User.builder()
            .email("mail@mail.ru")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация логина, если его нет"
        );
    }

    @Test
    void loginValidation_ifBlank_shouldThrowValidationException() {
        User user = User.builder()
            .login("")
            .email("mail@mail.ru")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация логина, если он состоит из пустой строки"
        );
    }

    @Test
    void loginValidation_ifContainsSpace_shouldThrowValidationException() {
        User user = User.builder()
            .login("login with space")
            .email("mail@mail.ru")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация логина, если он содержит пробелы"
        );

        violations.stream().map(v -> v.getInvalidValue())
            .forEach(System.out::println);
    }

    @Test
    void nameValidation_ifNull_shouldUseLogin() {
        User user = User.builder()
            .login("login")
            .email("mail@mail.ru")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        userController.validate(user);
        assertEquals(user.getLogin(), user.getName(), "Не подставился логин в поле имя, если оно было пустым");
    }

    @Test
    void birthdayValidation_ifInFuture_shouldThrowValidationException() {
        User user = User.builder()
            .login("login")
            .email("mail@mail.ru")
            .name("name")
            .birthday(LocalDate.of(2446, 8, 20))
            .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
            violations.isEmpty(),
            "Не прошла валидация дня рождения, если он в будущем"
        );
    }
}
