package com.eidiko.User_Role.repository;

import com.eidiko.User_Role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role ,Integer> {
    Optional<Role> findByName(String name);
}
