package com.aidannolan.backend.service.trivia;

import com.aidannolan.backend.dto.trivia.CategoryDTO;
import com.aidannolan.backend.dto.trivia.QuestionDTO;
import com.aidannolan.backend.enums.Difficulty;
import com.aidannolan.backend.external.opentdb.client.TriviaApiClient;
import com.aidannolan.backend.external.opentdb.dto.OpenTriviaCategory;
import com.aidannolan.backend.external.opentdb.dto.OpenTriviaQuestion;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TriviaServiceImpl implements TriviaService {
    private final TriviaApiClient triviaApiClient;

    @Override
    public List<QuestionDTO> getQuestions(int amount, Integer category, Difficulty difficulty) {
        return triviaApiClient
                .getQuestions(amount, category, difficulty)
                .stream()
                .map(this::toQuestionDTO)
                .toList();
    }

    private QuestionDTO toQuestionDTO(OpenTriviaQuestion question) {
        List<String> answers = new ArrayList<>();

        answers.add(decode(question.getCorrectAnswer()));

        question.getIncorrectAnswers()
                .stream()
                .map(this::decode)
                .forEach(answers::add);

        Collections.shuffle(answers);

        return QuestionDTO.builder()
                .id(question.getId())
                .category(decode(question.getCategory()))
                .difficulty(
                        Difficulty.valueOf(
                                question.getDifficulty()
                                        .toUpperCase()
                        )
                )
                .type(question.getType())
                .question(
                        decode(question.getQuestion())
                )
                .answers(answers)
                .build();
    }

    private String decode(String text) {
        return StringEscapeUtils.unescapeHtml4(text);
    }

    @Cacheable("triviaCategories")
    @Override
    public List<CategoryDTO> getCategories() {
        return triviaApiClient
                .getCategories()
                .stream()
                .map(this::toCategoryDTO)
                .toList();
    }

    private CategoryDTO toCategoryDTO(OpenTriviaCategory category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(decode(category.getName()))
                .build();
    }
}
