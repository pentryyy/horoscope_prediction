package com.pentryyy.horoscope_prediction.controller;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pentryyy.horoscope_prediction.model.Role;
import com.pentryyy.horoscope_prediction.model.User;
import com.pentryyy.horoscope_prediction.service.RoleService;
import com.pentryyy.horoscope_prediction.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    
    @PatchMapping("/change-role/{id}")
    public ResponseEntity<?> changeRole(@PathVariable Long id, @RequestParam Short roleId) {
        Optional<User> optionalUser = userService.findById(id);
        Optional<Role> optionalRole = roleService.findById(roleId);

        // Пытаемся найти пользователя
        if (!optionalUser.isPresent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Пользователь не найден");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jsonObject.toString());
        }
        User user = optionalUser.get();

        // Пытаемся найти роль
        if (!optionalRole.isPresent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Роль не найдена");

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jsonObject.toString());
        }
        Role role = optionalRole.get();

        user.setRole(role);
        userService.save(user);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", role.getId());

        return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(jsonObject.toString());
    }

    @PatchMapping("/disable-user/{id}")
    public ResponseEntity<?> disableUser(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);

        // Пытаемся найти пользователя
        if (!optionalUser.isPresent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Пользователь не найден");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jsonObject.toString());
        }
        User user = optionalUser.get();
        
        if (!user.getIsEnabled()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Пользователь уже отключен");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jsonObject.toString());
        }

        user.setIsEnabled(false);
        userService.save(user);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Пользователь отключен");
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(jsonObject.toString());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody Map<String, Object> updates) {
        Optional<User> optionalUser = userService.findById(id);
                                            
        // Пытаемся найти пользователя
        if (!optionalUser.isPresent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Пользователь не найден");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jsonObject.toString());
        }
        User user = optionalUser.get();

        // Обновляем поля, если они присутствуют в запросе
        if (updates.containsKey("username")) {
            String username = (String) updates.get("username");
            user.setUsername(username);
        }

        if (updates.containsKey("email")) {
            String email = (String) updates.get("email");
            user.setEmail(email);
        }

        if (updates.containsKey("birthDate")) {
            String birthDateStr = (String) updates.get("birthDate");
            LocalDate birthDate = LocalDate.parse(birthDateStr);
            user.setBirthDate(birthDate);
        }

        if (updates.containsKey("gender")) {
            String gender = (String) updates.get("gender");
            user.setGender(gender);
        }
        userService.save(user);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Пользователь обновлен успешно");

        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(jsonObject.toString());
    }
}