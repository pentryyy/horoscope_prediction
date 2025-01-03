package com.pentryyy.horoscope_prediction.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Пользователь с email - " + email + " уже существует");
    }
}
