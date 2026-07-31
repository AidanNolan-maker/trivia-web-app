package com.aidannolan.backend.controller;

import com.aidannolan.backend.config.OpenApiConfig;
import com.aidannolan.backend.dto.game.*;
import com.aidannolan.backend.enums.Difficulty;
import com.aidannolan.backend.service.game.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Games",
        description = "Game session operations"
)
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class GameController {
    private final GameService gameService;

    @PostMapping
    @Operation(summary = "Start a new game")
    public ResponseEntity<GameSessionResponseDTO> startGame(
            @Valid @RequestBody StartGameRequestDTO request
    ) {
        return ResponseEntity.ok(
                gameService.startGame(request)
        );
    }

    @PostMapping("/{gameId}/questions/{questionId}/answer")
    @Operation(summary = "Submit an answer")
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
    @Operation(summary = "get all the games")
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

    @DeleteMapping("/{gameId}")
    @Operation(summary = "Delete a game")
    public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
        gameService.deleteGame(gameId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{gameId}")
    @Operation(summary = "Get a game")
    public ResponseEntity<GameSessionResponseDTO> getGame(
            @PathVariable Long gameId
    ) {
        return ResponseEntity.ok(gameService.getGame(gameId));
    }
}
