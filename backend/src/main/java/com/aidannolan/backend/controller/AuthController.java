package com.aidannolan.backend.controller;

import com.aidannolan.backend.dto.auth.LoginRequestDTO;
import com.aidannolan.backend.dto.auth.LoginResponseDTO;
import com.aidannolan.backend.dto.auth.RegisterRequestDTO;
import com.aidannolan.backend.dto.auth.RegisterResponseDTO;
import com.aidannolan.backend.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponseDTO register(@Valid @RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
