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
import com.pentryyy.horoscope_prediction.exception.EmailAlreadyExistsException;
import com.pentryyy.horoscope_prediction.exception.PredictionDoesNotExistException;
import com.pentryyy.horoscope_prediction.exception.RoleDoesNotExistException;
import com.pentryyy.horoscope_prediction.exception.RolenameAlreadyExistsException;
import com.pentryyy.horoscope_prediction.exception.UserAlreadyDisabledException;
import com.pentryyy.horoscope_prediction.exception.UserAlreadyEnabledException;
import com.pentryyy.horoscope_prediction.exception.UserDoesNotExistException;
import com.pentryyy.horoscope_prediction.exception.UsernameAlreadyExistsException;

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

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<Map<String, String>> handleUserDoesNotExistException(UserDoesNotExistException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RoleDoesNotExistException.class)
    public ResponseEntity<Map<String, String>> handleRoleDoesNotExistException(RoleDoesNotExistException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyDisabledException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyDisabledException(UserAlreadyDisabledException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyEnabledException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyEnabledException(UserAlreadyEnabledException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(RolenameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleRolenameAlreadyExistsException(RolenameAlreadyExistsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(PredictionDoesNotExistException.class)
    public ResponseEntity<Map<String, String>> handlePredictionDoesNotExistException(PredictionDoesNotExistException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
