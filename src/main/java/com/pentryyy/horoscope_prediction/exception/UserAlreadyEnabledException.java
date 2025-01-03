package com.pentryyy.horoscope_prediction.exception;

public class UserAlreadyEnabledException extends RuntimeException {
    public UserAlreadyEnabledException() {
        super("Пользователь уже активен");
    }
}
