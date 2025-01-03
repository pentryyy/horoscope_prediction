package com.pentryyy.horoscope_prediction.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Запрос на смену пароля")
public class RoleUpdateRequest {
    @Schema(description = "Роль", example = "ROLE_MANAGER")
    @NotBlank(message = "Роль не может быть пустой")
    @Pattern(
        regexp = "^ROLE_[A-Z0-9]+$", 
        message = "Роль должна начинаться с 'ROLE_' и содержать только цифры и заглавные буквы")
    private String rolename;
}