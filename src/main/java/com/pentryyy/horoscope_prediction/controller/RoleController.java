package com.pentryyy.horoscope_prediction.controller;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pentryyy.horoscope_prediction.model.Role;
import com.pentryyy.horoscope_prediction.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/get-all-roles")
    public List<Role> getAllRoles() {
        List<Role> rolesList = roleService.findAll();
        return rolesList;
    }

    @GetMapping("/get-role/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Short id) {
        Optional<Role> optionalRole = roleService.findById(id);

        if (!optionalRole.isPresent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Роль не найдена");
           
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(jsonObject.toString());   
        }

        Role role = optionalRole.get();
        return ResponseEntity.ok(role);
    }

    @PostMapping("/create-role")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        JSONObject jsonObject = new JSONObject();
        Optional<Role> existRole = roleService.findByRolename(role.getRolename());

        if (!existRole.isPresent()){
            roleService.save(role);

            jsonObject.put("id", role.getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(jsonObject.toString());   
        }
 
        jsonObject.put("message", "Роль с таким названием уже существует");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(jsonObject.toString());   
    }
 
    @PatchMapping("update-role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Short id, @RequestBody Role updatedRole) {
        Optional<Role> optionalRole = roleService.findById(id);

        if (!optionalRole.isPresent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Роль не найдена");
           
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(jsonObject.toString());   
        }
        Role role = optionalRole.get();

        role.setRolename(updatedRole.getRolename());
        roleService.save(role);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", role.getId());
        
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(jsonObject.toString());   
    }

    @DeleteMapping("/delete-role/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Short id) {
        if (!roleService.existsById(id)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Роль не найдена");
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(jsonObject.toString());   
        }
        
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}