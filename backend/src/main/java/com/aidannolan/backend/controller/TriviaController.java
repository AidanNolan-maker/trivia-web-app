package com.aidannolan.backend.controller;

import com.aidannolan.backend.config.OpenApiConfig;
import com.aidannolan.backend.dto.trivia.CategoryDTO;
import com.aidannolan.backend.dto.trivia.QuestionDTO;
import com.aidannolan.backend.enums.Difficulty;
import com.aidannolan.backend.service.trivia.TriviaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trivia")
@RequiredArgsConstructor
@Tag(
        name = "Trivia",
        description = "Trivia question operations"
)
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class TriviaController {
    private final TriviaService triviaService;

    @GetMapping("/questions")
    @Operation(summary = "Get the questions")
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

    @GetMapping("/categories")
    @Operation(summary = "Get the categories")
    public List<CategoryDTO> getCategories() {
        return triviaService.getCategories();
    }
}
