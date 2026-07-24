package com.aidannolan.backend.service.auth;

import com.aidannolan.backend.dto.auth.LoginRequestDTO;
import com.aidannolan.backend.dto.auth.LoginResponseDTO;
import com.aidannolan.backend.dto.auth.RegisterRequestDTO;
import com.aidannolan.backend.dto.auth.RegisterResponseDTO;
import com.aidannolan.backend.entity.User;
import com.aidannolan.backend.enums.UserRole;
import com.aidannolan.backend.exception.EmailAlreadyExistsException;
import com.aidannolan.backend.exception.InvalidCredentialsException;
import com.aidannolan.backend.exception.ResourceNotFoundException;
import com.aidannolan.backend.exception.UsernameAlreadyExistsException;
import com.aidannolan.backend.mapper.UserMapper;
import com.aidannolan.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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

        user.setEnabled(true);
        user.setAccountLocked(false);

        user = userRepository.save(user);

        return userMapper.toRegisterResponseDTO(user);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException("Invalid username or password.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByUsername(
                    userDetails.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        String token = jwtService.generateToken(userDetails);

        LoginResponseDTO response = userMapper.toLoginResponseDTO(user);

        response.setToken(token);

        return response;
    }
}
