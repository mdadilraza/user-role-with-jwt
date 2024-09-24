package com.eidiko.User_Role.dto;


import com.eidiko.User_Role.entity.Role;
import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String role;
}