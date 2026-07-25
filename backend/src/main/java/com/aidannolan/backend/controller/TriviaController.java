package com.aidannolan.backend.controller;

import com.aidannolan.backend.dto.trivia.QuestionDTO;
import com.aidannolan.backend.enums.Difficulty;
import com.aidannolan.backend.service.trivia.TriviaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trivia")
@RequiredArgsConstructor
public class TriviaController {
    private final TriviaService triviaService;

    @GetMapping("/questions")
    public List<QuestionDTO> getQuestions(
            @RequestParam(defaultValue = "10")
            int amount,

            @RequestParam(required = false)
            Integer category,

            @RequestParam(required = false)
            Difficulty difficulty
    ) {
        return triviaService.getQuestions(amount, category, difficulty);
    }
}
