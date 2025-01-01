package com.pentryyy.horoscope_prediction.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pentryyy.horoscope_prediction.dto.PassChangeRequest;
import com.pentryyy.horoscope_prediction.model.Role;
import com.pentryyy.horoscope_prediction.model.User;
import com.pentryyy.horoscope_prediction.service.RoleService;
import com.pentryyy.horoscope_prediction.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/get-all-users")
    public List<User> getAllUsers() {
        List<User> usersList = userService.findAll();
        return usersList;
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);

        if (!optionalUser.isPresent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Пользователь не найден");
           
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(jsonObject.toString());   
        }

        User user = optionalUser.get();
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/change-role/{id}")
    public ResponseEntity<?> changeRole(@PathVariable Long id,  @RequestBody Role requestRole) {                
        Optional<User> optionalUser = userService.findById(id);
        Optional<Role> optionalRole = roleService.findById(requestRole.getId());

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
        jsonObject.put("rolename", role.getRolename())
                  .put("user_id", user.getId());

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

    @PatchMapping("/enable-user/{id}")
    public ResponseEntity<?> enableUser(@PathVariable Long id) {
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
        
        if (user.getIsEnabled()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Пользователь уже активен");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(jsonObject.toString());
        }

        user.setIsEnabled(true);
        userService.save(user);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Пользователь активен");
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(jsonObject.toString());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> requestData) {        
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
        
        // Обновляем данные пользователя если поля не пусты
        if (requestData.containsKey("username")) {
            user.setUsername((String) requestData.get("username"));
        }
        if (requestData.containsKey("email")) {
            user.setEmail((String) requestData.get("email"));
        }
        if (requestData.containsKey("gender")) {
            user.setGender((String) requestData.get("gender"));
        }
        if (requestData.containsKey("birthDate")) {
            user.setBirthDate(LocalDate.parse((String) requestData.get("birthDate")));
        }
        userService.save(user);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Данные пользователя обновлены");
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(jsonObject.toString());
    }

    @PatchMapping("/change-pass/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody @Valid PassChangeRequest request) {
        Optional<User> optionalUser = userService.findById(id);

        // Пытаемся найти пользователя
        if (!optionalUser.isPresent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Пользователь не найден");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(jsonObject.toString());
        }

        User user                = optionalUser.get();
        String newPassword       = request.getPassword();
        String encryptedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encryptedPassword);
        userService.save(user);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Пароль успешно обновлен");
        return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(jsonObject.toString());
    }
}