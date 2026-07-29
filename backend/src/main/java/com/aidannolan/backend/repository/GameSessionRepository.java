package com.aidannolan.backend.repository;

import com.aidannolan.backend.entity.GameSession;
import com.aidannolan.backend.enums.Difficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    List<GameSession> findByUserIdOrderByStartedAtDesc(Long userId);

    Optional<GameSession> findByIdAndUserId(Long id, Long userId);

    Page<GameSession> findByUserId(Long userId, Pageable pageable);

    Page<GameSession> findByUserIdAndFinishedAtIsNotNull(Long userId, Pageable pageable);

    Page<GameSession> findByUserIdAndFinishedAtIsNull(Long userId, Pageable pageable);

    Page<GameSession> findByUserIdAndDifficulty(Long userId, Difficulty difficulty, Pageable pageable);

    Page<GameSession> findByUserIdAndCategoryId(Long userId, Integer categoryId, Pageable pageable);
}
