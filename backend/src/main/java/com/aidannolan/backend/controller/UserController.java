package com.aidannolan.backend.controller;

import com.aidannolan.backend.dto.user.CurrentUserResponseDTO;
import com.aidannolan.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public CurrentUserResponseDTO getCurrentUser(Authentication authentication) {
        return userService.getCurrentUser(authentication.getName());
    }
}
