package com.pentryyy.horoscope_prediction.exception;

public class UserDoesNotExistException extends RuntimeException  {
    public UserDoesNotExistException(Long id){
        super("Пользователь с id " + id + " не найден");
    }
}
