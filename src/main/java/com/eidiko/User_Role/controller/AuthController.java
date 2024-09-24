package com.eidiko.User_Role.controller;

import com.eidiko.User_Role.dto.AuthResponseDto;
import com.eidiko.User_Role.dto.LoginDto;
import com.eidiko.User_Role.dto.RegisterDto;
import com.eidiko.User_Role.entity.AppUser;
import com.eidiko.User_Role.entity.Role;
import com.eidiko.User_Role.repository.AppUserRepository;
import com.eidiko.User_Role.repository.RoleRepository;
import com.eidiko.User_Role.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AppUserRepository appUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtGenerator jwtGenerator;
    @Autowired
    public AuthController(AppUserRepository appUserRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtGenerator=jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

        //if (appUserRepository.existsByUserName(registerDto.getUsername())) {
             if(appUserRepository.findByUserName(registerDto.getUsername()).isPresent()){
        return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        AppUser appUser = new AppUser();
        appUser.setUserName(registerDto.getUsername());
        appUser.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role roles = roleRepository.findByName(registerDto.getRole()).get();
        appUser.setRoles(Collections.singletonList(roles));

        appUserRepository.save(appUser);

        System.out.println("User saved in data base ");

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token,appUserRepository.findByUserName(loginDto.getUsername()).get()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
      AppUser appUser  = appUserRepository.findById(id).get();
        appUserRepository.delete(appUser);
        return ResponseEntity.ok("deleted ");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id , @RequestBody RegisterDto registerDto)
    {
        AppUser appUser = appUserRepository.findById(id).get();
        appUser.setUserName(registerDto.getUsername());
        appUser.setPassword(registerDto.getPassword());
        Optional<Role> byName = roleRepository.findByName(registerDto.getRole());
        appUser.setRoles(Collections.singletonList(byName.get()));

        appUserRepository.save(appUser);
        return ResponseEntity.ok(appUser);
    }

}