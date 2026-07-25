package com.aidannolan.backend.service.user;

import com.aidannolan.backend.dto.user.CurrentUserResponseDTO;
import com.aidannolan.backend.entity.User;

public interface UserService {
    CurrentUserResponseDTO getCurrentUser(String username);
}
