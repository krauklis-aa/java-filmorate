package ru.yandex.practicum.filmorate.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.constraints.Past;

import ru.yandex.practicum.filmorate.validator.IsAfterMinReleaseDateValidator;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsAfterMinReleaseDateValidator.class)
@Past
public @interface IsAfterMinReleaseDate {
    String message() default "Date must not be before {value}";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    String value() default "1895-12-28";
}

