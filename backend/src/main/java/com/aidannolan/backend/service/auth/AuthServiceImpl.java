package com.aidannolan.backend.service.auth;

import com.aidannolan.backend.dto.auth.RegisterRequestDTO;
import com.aidannolan.backend.dto.auth.RegisterResponseDTO;
import com.aidannolan.backend.entity.User;
import com.aidannolan.backend.enums.UserRole;
import com.aidannolan.backend.exception.EmailAlreadyExistsException;
import com.aidannolan.backend.exception.UsernameAlreadyExistsException;
import com.aidannolan.backend.mapper.UserMapper;
import com.aidannolan.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);

        user = userRepository.save(user);

        return userMapper.toRegisterResponseDTO(user);
    }
}
