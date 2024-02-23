package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
