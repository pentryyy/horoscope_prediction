package com.pentryyy.horoscope_prediction.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Запрос на смену пароля")
public class PasswordChangeRequest {
    @Schema(description = "Пароль", example = "my_1secret1_password")
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    private String password;
}