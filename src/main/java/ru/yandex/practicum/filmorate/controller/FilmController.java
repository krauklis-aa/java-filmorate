package ru.yandex.practicum.filmorate.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 0;

    //добавление фильма
    @PostMapping()
    public Film create(@RequestBody Film film) {
        validate(film);
        film.setId(++id);
        films.put(film.getId(), film);
        return film;
    }

    //обновление фильма
    @PutMapping()
    public Film update(@RequestBody Film film) {
        int filmId = film.getId();
        if (!films.containsKey(filmId)) {
            throw new ValidationException(String.format("Фильм с id %d не найден", filmId));
        }
        validate(film);
        films.put(filmId, film);
        return film;
    }

    //получение всех фильмов
    @GetMapping()
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    //валидация
    public void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание должно быть менее 200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза должна быть позже 28 декабря 1895 года");
        }

        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
