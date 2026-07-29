package com.aidannolan.backend.dto.game;

import com.aidannolan.backend.enums.Difficulty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class StartGameRequestDTO {
    @Min(1)
    @Max(50)
    private int amount = 10;

    private Integer category;

    private Difficulty difficulty;
}
