package com.aidannolan.backend.repository;

import com.aidannolan.backend.entity.GameQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameQuestionRepository extends JpaRepository<GameQuestion, Long> {
    Optional<GameQuestion> findByIdAndGameSessionId(Long questionId, Long gameSessionId);
}
