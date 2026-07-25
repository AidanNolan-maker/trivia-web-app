package com.aidannolan.backend.dto.trivia;

import com.aidannolan.backend.enums.Difficulty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionDTO {
    private Long id;

    private String category;

    private Difficulty difficulty;

    private String type;

    private String question;

    private List<String> answers;
}
