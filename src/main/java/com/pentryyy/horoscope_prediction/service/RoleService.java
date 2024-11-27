package com.pentryyy.horoscope_prediction.service;

import java.util.List;
import java.util.NoSuchElementException;
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

    public Role findByRolename(String rolename){
        return roleRepository.findByRolename(rolename)
                             .orElseThrow(() -> new NoSuchElementException("Роль '" + rolename + "' не найдена"));
    }
}