package com.aidannolan.backend.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentUserResponseDTO {
    private String username;
    private String role;
}
