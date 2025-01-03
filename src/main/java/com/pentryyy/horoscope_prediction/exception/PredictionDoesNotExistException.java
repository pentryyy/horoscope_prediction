package com.pentryyy.horoscope_prediction.exception;

public class PredictionDoesNotExistException extends RuntimeException  {
    public PredictionDoesNotExistException(Long id){
        super("Предсказание с id " + id + " не найдено");
    }
}
