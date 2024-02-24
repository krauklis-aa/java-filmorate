package ru.yandex.practicum.filmorate.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Service
public class FilmService {
    private final FilmStorage storage;

    @Autowired
    public FilmService(FilmStorage storage) {
        this.storage = storage;
    }

    public void addLike(int id, int userId) {
        Film film = storage.getById(id);

        if (userId <= 0) {
            throw new NotFoundException("Неверный userId");
        }
        film.addLike(userId);
    }

    public void deleteLike(int id, int userId) {
        Film film = storage.getById(id);

        if (userId <= 0) {
            throw new NotFoundException("Неверный userId");
        }
        film.deleteLike(userId);
    }

    public List<Film> getPopular(int count) {
        return storage.getAll().stream()
            .sorted(Comparator.comparingInt(Film::getLikes).reversed())
            .limit(count)
            .collect(Collectors.toList());
    }

    public Film create(Film film) {
        return storage.create(film);
    }

    public Film update(Film film) {
        return storage.update(film);
    }

    public List<Film> getAll() {
        return storage.getAll();
    }

    public Film getById(int id) {
        return storage.getById(id);
    }
}
