package com.aidannolan.backend.repository;

import com.aidannolan.backend.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;
import java.util.Optional;

public interface GameSessionRepository extends JpaRepository<GameSession, Long>,
JpaSpecificationExecutor<GameSession> {
    Optional<GameSession> findByIdAndUserId(Long id, Long userId);

    List<GameSession> findByUserId(Long userId);
}
