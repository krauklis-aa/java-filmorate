package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    String error;
    HttpStatus status;

    public ErrorResponse(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
