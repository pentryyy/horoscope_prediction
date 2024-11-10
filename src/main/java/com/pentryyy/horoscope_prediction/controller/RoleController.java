package com.pentryyy.horoscope_prediction.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public List<Role> getRoles() {
        List<Role> rolesList = roleService.findAll();
        return rolesList;
    }

    @GetMapping("/get-role/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Short id) {
        Optional<Role> optionalRole = roleService.findById(id);

        if (!optionalRole.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Role role = optionalRole.get();
        return ResponseEntity.ok(role);
    }

    @PostMapping("/create-role")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        roleService.save(role);
        return ResponseEntity.ok(role);
    }
 
    @PutMapping("update-role/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Short id, @RequestBody Role updatedRole) {
        Optional<Role> optionalRole = roleService.findById(id);

        if (!optionalRole.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Role role = optionalRole.get();
        role.setRolename(updatedRole.getRolename());
        roleService.save(role);

        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/delete-role/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Short id) {
        if (roleService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        roleService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}