package com.eidiko.User_Role.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@OpenAPIDefinition(security = @SecurityRequirement(name = "jwt"))
@SecurityScheme(name = "jwt" ,  description = "jwt Authentication",
        bearerFormat = "jwt",
        in = SecuritySchemeIn.HEADER,

        type = SecuritySchemeType.HTTP,
        scheme = "Bearer"
)
public class SwaggerConfig {

}
