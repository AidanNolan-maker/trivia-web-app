package com.aidannolan.backend.dto.game;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubmitAnswerRequestDTO {
    @NotBlank
    private String selectedAnswer;
}
