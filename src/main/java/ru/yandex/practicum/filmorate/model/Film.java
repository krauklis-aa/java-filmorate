package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private String genre;
    private String rating;
    private final Set<Integer> usersWhoLiked = new HashSet<>();

    public void addLike(int userId) {
        usersWhoLiked.add(userId);
    }

    public void deleteLike(int userId) {
        usersWhoLiked.remove(userId);
    }

    public int getLikes() {
        return usersWhoLiked.size();
    }
}
