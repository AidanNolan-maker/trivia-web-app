package com.aidannolan.backend.external.opentdb.client;

import com.aidannolan.backend.enums.Difficulty;
import com.aidannolan.backend.external.opentdb.dto.OpenTriviaQuestion;

import java.util.List;


public interface TriviaApiClient {
    List<OpenTriviaQuestion> getQuestions(int amount, Integer category, Difficulty difficulty);
}
