package com.aidannolan.backend.service.trivia;

import com.aidannolan.backend.dto.trivia.QuestionDTO;
import com.aidannolan.backend.enums.Difficulty;

import java.util.List;

public interface TriviaService {
    List<QuestionDTO> getQuestions(int amount, Integer category, Difficulty difficulty);
}
