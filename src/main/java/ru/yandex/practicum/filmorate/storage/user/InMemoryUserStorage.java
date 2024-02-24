package ru.yandex.practicum.filmorate.storage.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public User create(User user) {
        validate(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;    }

    @Override
    public User update(User user) {
        int userId = user.getId();
        if (!users.containsKey(userId)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
        }
        validate(user);
        users.put(userId, user);
        return user;    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return users.get(id);
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
