package com.pentryyy.horoscope_prediction.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pentryyy.horoscope_prediction.dto.RoleUpdateRequest;
import com.pentryyy.horoscope_prediction.exception.RoleDoesNotExistException;
import com.pentryyy.horoscope_prediction.exception.RolenameAlreadyExistsException;
import com.pentryyy.horoscope_prediction.model.Role;
import com.pentryyy.horoscope_prediction.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findById(Short id) {
        return roleRepository.findById(id)
                             .orElseThrow(() -> new RoleDoesNotExistException(id));
    }

    public Page<Role> getAllRoles(
        int page, 
        int limit, 
        String sortBy, 
        String sortOrder) {
        
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        return roleRepository.findAll(PageRequest.of(page, limit, sort));
    }

    public Optional<Role> findByRolename(String rolename){
        return roleRepository.findByRolename(rolename);
    }

    public Role createRole(RoleUpdateRequest request) {
        if (roleRepository.existsByRolename(request.getRolename())) {
            throw new RolenameAlreadyExistsException(request.getRolename());
        }
        
        Role role = Role.builder()
                        .rolename(request.getRolename())
                        .build();
        return roleRepository.save(role);
    }

    public void updateRole(
        Short id, 
        RoleUpdateRequest request) {
        
        Role role = findById(id);

        if (roleRepository.existsByRolename(request.getRolename())) {
            throw new RolenameAlreadyExistsException(request.getRolename());
        }

        role.setRolename(request.getRolename());
        roleRepository.save(role);
    }

    public void deleteRole(Short id) {
        if (!roleRepository.existsById(id)) {
            throw new RoleDoesNotExistException(id);
        }

        roleRepository.deleteById(id);
    }
}
