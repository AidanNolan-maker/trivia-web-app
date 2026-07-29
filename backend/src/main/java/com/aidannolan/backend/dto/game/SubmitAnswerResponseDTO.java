package com.aidannolan.backend.dto.game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmitAnswerResponseDTO {
    private Boolean correct;

    private int score;

    private int correctAnswers;

    private int questionsAnswered;

    private int totalQuestions;

    private Boolean gameFinished;
}
