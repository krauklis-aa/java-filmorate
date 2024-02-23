package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import ru.yandex.practicum.filmorate.annotation.IsAfterMinReleaseDate;

/**
 * Film.
 */
@Data
@Builder
@AllArgsConstructor
public class Film {
    private int id;
    @NotNull
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @IsAfterMinReleaseDate
    private LocalDate releaseDate;
    @Positive
    private int duration;
}
