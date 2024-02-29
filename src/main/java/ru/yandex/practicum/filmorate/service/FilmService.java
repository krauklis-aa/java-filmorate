package ru.yandex.practicum.filmorate.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;


    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int id, int userId) {
        Film film = filmStorage.getById(id);

        if (userId <= 0 || userStorage.getById(userId) == null) {
            throw new NotFoundException(String.format("Неверный userId: %d", userId));
        }
        film.addLike(userId);
    }

    public void deleteLike(int id, int userId) {
        Film film = filmStorage.getById(id);

        if (userId <= 0 || userStorage.getById(userId) == null) {
            throw new NotFoundException(String.format("Неверный userId: %d", userId));
        }
        film.deleteLike(userId);
    }

    public List<Film> getPopular(int count) {
        return filmStorage.getAll().stream()
            .sorted(Comparator.comparingInt(Film::getLikes).reversed())
            .limit(count)
            .collect(Collectors.toList());
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film getById(int id) {
        return filmStorage.getById(id);
    }
}
