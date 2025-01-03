package com.pentryyy.horoscope_prediction.exception;

public class RolenameAlreadyExistsException extends RuntimeException {
    public RolenameAlreadyExistsException(String rolename) {
        super("Роль с названием - " + rolename + " уже существует");
    }
}
