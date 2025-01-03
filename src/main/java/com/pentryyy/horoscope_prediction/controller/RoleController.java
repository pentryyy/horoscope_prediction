package com.pentryyy.horoscope_prediction.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pentryyy.horoscope_prediction.model.Role;
import com.pentryyy.horoscope_prediction.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/get-all-roles")
    public ResponseEntity<Page<Role>> getAllRoles(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder) {
        
        Page<Role> roles = roleService.getAllRoles(
            page, 
            limit, 
            sortBy, 
            sortOrder);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/get-role/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Short id) {
        Role role = roleService.findById(id);
        return ResponseEntity.ok(role);
    }

    @PostMapping("/create-role")
    public ResponseEntity<?> createRole(@RequestBody Role request) {
        Role role = roleService.createRole(request);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", role.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(jsonObject.toString());   
    }
 
    @PatchMapping("update-role/{id}")
    public ResponseEntity<?> updateRole(
        @PathVariable Short id, 
        @RequestBody Role request) {
        
        roleService.updateRole(id, request);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Данные роли обновлены");
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(jsonObject.toString());   
    }

    @DeleteMapping("/delete-role/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Short id) {    
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}