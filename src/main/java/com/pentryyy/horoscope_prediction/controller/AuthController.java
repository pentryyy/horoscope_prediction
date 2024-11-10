package com.pentryyy.horoscope_prediction.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pentryyy.horoscope_prediction.dto.JwtAuthenticationResponse;
import com.pentryyy.horoscope_prediction.dto.SignInRequest;
import com.pentryyy.horoscope_prediction.dto.SignUpRequest;
import com.pentryyy.horoscope_prediction.model.ResponseMessage;
import com.pentryyy.horoscope_prediction.model.RoleEnum;
import com.pentryyy.horoscope_prediction.model.User;
import com.pentryyy.horoscope_prediction.service.AuthenticationService;
import com.pentryyy.horoscope_prediction.service.UserService;

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

    @Autowired
    private UserService userService;
    
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.signUp(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(new ResponseMessage(e.getMessage()));
        }
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @GetMapping("/get-all-users")
    public List<User> getAllUsers() {
        List<User> usersList = userService.findAll();
        return usersList;
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.existsById(id)) {
            userService.deleteById(id);
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/change-role-{role}/{id}")
    public ResponseEntity<?> changeRole(@PathVariable Long id, @PathVariable String role) {
        Optional<User> optionalUser = userService.findById(id);

        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Пользователь не найден"));
        }
        
        User user = optionalUser.get();
        switch (role) {
            case "admin":
                user.setRole(RoleEnum.ROLE_ADMIN.getValue());
                break;
            case "user":
                user.setRole(RoleEnum.ROLE_USER.getValue());
                break;
        }
        userService.save(user);

        return ResponseEntity.ok(new ResponseMessage("Роль успешно обновлена"));
    }
}