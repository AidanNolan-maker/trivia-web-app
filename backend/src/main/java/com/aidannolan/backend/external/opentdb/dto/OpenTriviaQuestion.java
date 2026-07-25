package com.aidannolan.backend.external.opentdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class OpenTriviaQuestion {
    private Long id;

    private String type;

    private String difficulty;

    private String category;

    private String question;

    @JsonProperty("correct_answer")
    private String correctAnswer;

    @JsonProperty("incorrect_answers")
    private List<String> incorrectAnswers;
}
