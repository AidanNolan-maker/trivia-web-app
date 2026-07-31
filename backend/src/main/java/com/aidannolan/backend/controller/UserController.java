package com.aidannolan.backend.controller;

import com.aidannolan.backend.config.OpenApiConfig;
import com.aidannolan.backend.dto.user.CurrentUserResponseDTO;
import com.aidannolan.backend.dto.user.UserStatisticsResponseDTO;
import com.aidannolan.backend.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(
        name = "Users",
        description = "User operations"
)
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Me page")
    public CurrentUserResponseDTO getCurrentUser(Authentication authentication) {
        return userService.getCurrentUser(authentication.getName());
    }

    @GetMapping("/me/statistics")
    @Operation(summary = "Get user statistics")
    public ResponseEntity<UserStatisticsResponseDTO> getStatistics() {
        return ResponseEntity.ok(userService.getStatistics());
    }
}
