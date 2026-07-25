package com.aidannolan.backend.service.trivia;

import com.aidannolan.backend.dto.trivia.QuestionDTO;
import com.aidannolan.backend.enums.Difficulty;
import com.aidannolan.backend.external.opentdb.client.TriviaApiClient;
import com.aidannolan.backend.external.opentdb.dto.OpenTriviaQuestion;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class TriviaServiceImpl implements TriviaService {
    private final TriviaApiClient triviaApiClient;

    @Override
    public List<QuestionDTO> getQuestions(int amount, Integer category, Difficulty difficulty) {
        List<OpenTriviaQuestion> questions =
                triviaApiClient.getQuestions(amount, category, difficulty);

        return questions.stream().map(this::convertQuestion).toList();

    }

    private QuestionDTO convertQuestion(OpenTriviaQuestion question) {
        List<String> answers = new ArrayList<>();

        answers.add(question.getCorrectAnswer());
        answers.addAll(question.getIncorrectAnswers());

        Collections.shuffle(answers);

        return QuestionDTO.builder()
                .id(question.getId())
                .category(question.getCategory())
                .difficulty(Difficulty.valueOf(
                        question.getDifficulty().toUpperCase()))
                .question(question.getQuestion())
                .answers(answers)
                .build();
    }
}
