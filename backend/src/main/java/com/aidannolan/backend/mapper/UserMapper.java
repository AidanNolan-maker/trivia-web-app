package com.aidannolan.backend.mapper;

import com.aidannolan.backend.dto.auth.LoginResponseDTO;
import com.aidannolan.backend.dto.auth.RegisterRequestDTO;
import com.aidannolan.backend.dto.auth.RegisterResponseDTO;
import com.aidannolan.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequestDTO dto);

    RegisterResponseDTO toRegisterResponseDTO(User user);

    LoginResponseDTO toLoginResponseDTO(User user);
}
