package com.aidannolan.backend.controller;

import com.aidannolan.backend.dto.game.*;
import com.aidannolan.backend.enums.Difficulty;
import com.aidannolan.backend.service.game.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    public ResponseEntity<GameSessionResponseDTO> startGame(
            @Valid @RequestBody StartGameRequestDTO request
    ) {
        return ResponseEntity.ok(
                gameService.startGame(request)
        );
    }

    @PostMapping("/{gameId}/questions/{questionId}/answer")
    public ResponseEntity<SubmitAnswerResponseDTO> submitAnswer(
            @PathVariable Long gameId,
            @PathVariable Long questionId,
            @Valid @RequestBody SubmitAnswerRequestDTO request
    ) {
        return ResponseEntity.ok(
                gameService.submitAnswer(gameId, questionId, request)
        );
    }

    @GetMapping
    public ResponseEntity<Page<GameSummaryResponseDTO>> getGames(
            @RequestParam(required = false)
            Boolean finished,

            @RequestParam(required = false)
            Difficulty difficulty,

            @RequestParam(required = false)
            Integer categoryId,

            @PageableDefault(size = 10)
            Pageable pageable
    ) {
        return ResponseEntity.ok(gameService.getGames(finished, difficulty, categoryId, pageable));
    }
}
