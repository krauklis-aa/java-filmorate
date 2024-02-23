package ru.yandex.practicum.filmorate;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest()
class UserControllerTests {

    UserController userController = new UserController();

    @Test
    void emailValidation_ifNull_shouldThrowValidationException() {
        User user = User.builder()
            .login("login")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> userController.validate(user),
            "Не прошла валидация емейла, если его нет"
        );

        assertEquals("Email не может быть пустым и должен содержать @", e.getMessage());
    }

    @Test
    void emailValidation_ifBlank_shouldThrowValidationException() {
        User user = User.builder()
            .email("  ")
            .login("login")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> userController.validate(user),
            "Не прошла валидация емейла, если он состоит из пробелов"
        );

        assertEquals("Email не может быть пустым и должен содержать @", e.getMessage());
    }

    @Test
    void emailValidation_ifNoAt_shouldThrowValidationException() {
        User user = User.builder()
            .email("mail.ru")
            .login("login")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> userController.validate(user),
            "Не прошла валидация емейла, если он не содержит @"
        );

        assertEquals("Email не может быть пустым и должен содержать @", e.getMessage());
    }

    @Test
    void loginValidation_ifNull_shouldThrowValidationException() {
        User user = User.builder()
            .email("mail@mail.ru")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> userController.validate(user),
            "Не прошла валидация логина, если его нет"
        );

        assertEquals("Логин не должен быть пустым", e.getMessage());
    }

    @Test
    void loginValidation_ifBlank_shouldThrowValidationException() {
        User user = User.builder()
            .login("")
            .email("mail@mail.ru")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> userController.validate(user),
            "Не прошла валидация логина, если он состоит из пустой строки"
        );

        assertEquals("Логин не должен быть пустым", e.getMessage());
    }

    @Test
    void loginValidation_ifContainsSpace_shouldThrowValidationException() {
        User user = User.builder()
            .login("login with space")
            .email("mail@mail.ru")
            .name("name")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> userController.validate(user),
            "Не прошла валидация логина, если он содержит пробелы"
        );

        assertEquals("Логин не должен содержать пробелы", e.getMessage());
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

        ValidationException e = assertThrows(
            ValidationException.class,
            () -> userController.validate(user),
            "Не прошла валидация дня рождения, если он в будущем"
        );

        assertEquals("День рождения не может быть в будущем", e.getMessage());
    }
}
