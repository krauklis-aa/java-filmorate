package ru.yandex.practicum.filmorate.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.yandex.practicum.filmorate.annotation.IsAfterMinReleaseDate;

public class IsAfterMinReleaseDateValidator implements ConstraintValidator<IsAfterMinReleaseDate, LocalDate> {
    private LocalDate minimumDate;

    @Override
    public void initialize(IsAfterMinReleaseDate constraintAnnotation) {
        minimumDate = LocalDate.parse(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value == null || !value.isBefore(minimumDate);
    }
}

