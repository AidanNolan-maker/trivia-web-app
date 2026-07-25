package com.aidannolan.backend.dto.trivia;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    private Integer id;

    private String name;
}
