package com.pentryyy.horoscope_prediction.exception;

public class RoleDoesNotExistException extends RuntimeException  {
    public RoleDoesNotExistException(Short id){
        super("Роль с id " + id + " не найдена");
    }
}
