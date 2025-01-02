package com.pentryyy.horoscope_prediction.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<Role> getAllRoles(int page, 
                                  int limit, 
                                  String sortBy, 
                                  String sortOrder) {
        
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        return roleRepository.findAll(PageRequest.of(page, limit, sort));
    }

    public void deleteById(Short id) {
        roleRepository.deleteById(id);
    }

    public boolean existsById(Short id) {
        return roleRepository.existsById(id);
    }

    public Optional<Role> findByRolename(String rolename){
        return roleRepository.findByRolename(rolename);
    }
}