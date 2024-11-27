package com.pentryyy.horoscope_prediction.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pentryyy.horoscope_prediction.dto.JwtAuthenticationResponse;
import com.pentryyy.horoscope_prediction.dto.SignInRequest;
import com.pentryyy.horoscope_prediction.dto.SignUpRequest;
import com.pentryyy.horoscope_prediction.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.signUp(request));
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", e.getMessage());
           
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(jsonObject.toString());   
        }
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}