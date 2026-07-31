package com.aidannolan.backend.service.game;

import com.aidannolan.backend.dto.game.*;
import com.aidannolan.backend.enums.Difficulty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface GameService {
    GameSessionResponseDTO startGame(StartGameRequestDTO request);

    SubmitAnswerResponseDTO submitAnswer(
            Long gameId,
            Long questionId,
            SubmitAnswerRequestDTO request
    );

    GameSessionResponseDTO getGame(Long gameId);

    Page<GameSummaryResponseDTO> getGames(Boolean finished, Difficulty difficulty, Integer categoryId, Pageable pageable);

    void deleteGame(Long gameId);
}
