package com.eidiko.User_Role.repository;

import com.eidiko.User_Role.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser , Integer> {
    Optional<AppUser> findByUserName(String username);

    boolean existsByUserName(String username);

}
