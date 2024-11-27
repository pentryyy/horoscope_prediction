package com.pentryyy.horoscope_prediction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pentryyy.horoscope_prediction.model.Role;
import com.pentryyy.horoscope_prediction.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    
    public Role save(Role role) {
        return roleRepository.save(role);
    }
 
    public Optional<Role> findById(Short id) {
        return roleRepository.findById(id);
    }

    public List<Role> findAll() {
        return roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public void deleteById(Short id) {
        roleRepository.deleteById(id);
    }

    public boolean existsById(Short id) {
        return roleRepository.existsById(id);
    }

    public Short getIdByRolename(String rolename) {
        Optional<Role> role = roleRepository.findByRolename(rolename);
        return role.map(Role::getId).orElse(null);
    }

    public String getRolenameById(Short id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.map(Role::getRolename).orElse(null);
    }
}
