package com.pentryyy.horoscope_prediction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pentryyy.horoscope_prediction.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Short>{

}
