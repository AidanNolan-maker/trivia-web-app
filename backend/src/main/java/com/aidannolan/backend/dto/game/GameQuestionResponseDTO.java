package com.aidannolan.backend.dto.game;

import com.aidannolan.backend.enums.Difficulty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameQuestionResponseDTO {
    private Long id;

    private String question;

    private String category;

    private Difficulty difficulty;

    private String type;

    private List<String> answers;

    private Boolean answered;

    private String selectedAnswer;

    private Boolean correct;
}
