package com.pentryyy.horoscope_prediction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.pentryyy.horoscope_prediction.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Short>{
    Optional<Role> findByRolename(String rolename);

    @NonNull
    Optional<Role> findById(@NonNull Short id);
}
