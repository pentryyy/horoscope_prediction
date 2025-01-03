package com.pentryyy.horoscope_prediction.exception;

public class UserAlreadyDisabledException extends RuntimeException {
    public UserAlreadyDisabledException() {
        super("Пользователь уже отключен");
    }
}
