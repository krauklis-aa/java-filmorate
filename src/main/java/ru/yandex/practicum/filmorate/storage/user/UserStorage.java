package ru.yandex.practicum.filmorate.storage.user;

import java.util.List;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    User create(User user);

    User update(User user);

    List<User> getAll();

    User getById(int id);
}
