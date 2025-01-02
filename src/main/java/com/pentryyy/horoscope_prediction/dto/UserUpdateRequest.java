package com.pentryyy.horoscope_prediction.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

import com.pentryyy.horoscope_prediction.enumeration.GenderType;

@Data
@Schema(description = "Запрос на обновление данных пользователя")
public class UserUpdateRequest {
    @Schema(description = "Имя пользователя", example = "Jon")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    private String username;

    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;
    
    @Schema(description = "Пол пользователя", example = "MALE")
    private GenderType gender;

    @Schema(description = "Дата рождения пользователя", example = "2003-05-23")
    @NotNull(message = "Дата рождения не может быть пустой")
    @Past(message = "Дата рождения не может быть текущим днем")
    private LocalDate birthDate;
}