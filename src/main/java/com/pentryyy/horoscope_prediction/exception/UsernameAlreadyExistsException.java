package com.pentryyy.horoscope_prediction.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Пользователь с именем - " + username + " уже существует");
    }
}
