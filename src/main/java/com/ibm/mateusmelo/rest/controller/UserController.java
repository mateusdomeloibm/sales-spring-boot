package com.ibm.mateusmelo.rest.controller;

import com.ibm.mateusmelo.domain.entity.User;
import com.ibm.mateusmelo.rest.dto.CredentialDTO;
import com.ibm.mateusmelo.rest.dto.CredentialResponseDTO;
import com.ibm.mateusmelo.security.jwt.JwtService;
import com.ibm.mateusmelo.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserServiceImpl service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserServiceImpl service, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody User user) {
        return service.save(user);
    }

    @PostMapping(value = "/auth")
    @ResponseStatus(HttpStatus.OK)
    public CredentialResponseDTO authenticate(@Valid @RequestBody CredentialDTO credential) {
        User user = User.builder()
                .username(credential.getUsername())
                .password(credential.getPassword())
                .build();

        String token = jwtService.generateToken(user);

        if (service.authenticate(user)) {
            return new CredentialResponseDTO(credential.getUsername(), token);
        }

        throw new BadCredentialsException("Bad credentials");
    }
}
