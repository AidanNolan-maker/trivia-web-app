package com.aidannolan.backend.dto.game;

import com.aidannolan.backend.enums.Difficulty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GameSessionResponseDTO {
    private Long id;

    private int score;

    private int correctAnswers;

    private int totalQuestions;

    private List<GameQuestionResponseDTO> questions;

    private Integer categoryId;

    private String categoryName;

    private Difficulty difficulty;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;
}
