package com.eidiko.User_Role.dto;

import com.eidiko.User_Role.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";
    private AppUser appUser;

    public AuthResponseDto(String accessToken, AppUser appUser) {
        this.accessToken = accessToken;

        this.appUser =appUser;
    }
}
