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
import ru.yandex.practicum.filmorate.model.Film;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 0;

    //добавление фильма
    @PostMapping()
    public Film create(@RequestBody @Valid Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    //обновление фильма
    @PutMapping()
    public Film update(@RequestBody @Valid Film film) {
        int filmId = film.getId();
        if (!films.containsKey(filmId)) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", filmId));
        }
        films.put(filmId, film);
        return film;
    }

    //получение всех фильмов
    @GetMapping()
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    private int generateId() {
        return ++id;
    }
}
