package ru.yandex.practicum.filmorate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;

    //создание пользователя
    @PostMapping()
    public User create(@RequestBody @Valid User user) {
        validate(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    //обновление пользователя
    @PutMapping()
    public User update(@RequestBody @Valid User user) {
        int userId = user.getId();
        if (!users.containsKey(userId)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
        }
        validate(user);
        users.put(userId, user);
        return user;
    }

    //пполучение списка всех пользователей
    @GetMapping()
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    //валидация
    public void validate(User user) {
        String login = user.getLogin().trim();
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(login);
        }
    }

    private int generateId() {
        return ++id;
    }
}
