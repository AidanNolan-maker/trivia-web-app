package com.aidannolan.backend.service.auth;

import com.aidannolan.backend.dto.auth.LoginRequestDTO;
import com.aidannolan.backend.dto.auth.LoginResponseDTO;
import com.aidannolan.backend.dto.auth.RegisterRequestDTO;
import com.aidannolan.backend.dto.auth.RegisterResponseDTO;

public interface AuthService {
    RegisterResponseDTO register(RegisterRequestDTO dto);

    LoginResponseDTO login(LoginRequestDTO request);
}
