package com.pentryyy.horoscope_prediction.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {

            String fieldName    = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            
            errors.put("error", "Некорректное значение для поля: " + fieldName);
            errors.put("message", errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormatException(InvalidFormatException ex) {
        JSONObject errorResponse = new JSONObject();

        // Проверяем, связано ли исключение с enum
        if (ex.getTargetType().isEnum()) {
            Class<?> enumType      = ex.getTargetType();
            Object[] enumConstants = enumType.getEnumConstants(); 

            // Преобразуем значения enum в строку
            String allowedValues = String.join(", ", 
                Arrays.stream(enumConstants)
                      .map(Object::toString)
                      .toArray(String[]::new)
            );

            errorResponse.put("error", "Некорректное значение для поля: " + ex.getPath().get(0).getFieldName());
            errorResponse.put("message", "Допустимые значения: [" + allowedValues + "]");
        } else {
            errorResponse.put("error", "Неверный формат данных");
            errorResponse.put("message", ex.getOriginalMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(errorResponse.toString());
    }
}