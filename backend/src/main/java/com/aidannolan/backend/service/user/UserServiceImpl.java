package com.aidannolan.backend.service.user;

import com.aidannolan.backend.dto.user.CurrentUserResponseDTO;
import com.aidannolan.backend.entity.User;
import com.aidannolan.backend.exception.ResourceNotFoundException;
import com.aidannolan.backend.mapper.UserMapper;
import com.aidannolan.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public CurrentUserResponseDTO getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        return userMapper.toCurrentUserResponseDTO(user);
    }
}
