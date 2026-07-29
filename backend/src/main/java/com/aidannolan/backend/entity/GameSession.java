package com.aidannolan.backend.entity;

import com.aidannolan.backend.enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "game_sessions")
public class GameSession extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int correctAnswers;

    @Column(nullable = false)
    private int totalQuestions;

    @Column
    private Integer categoryId;

    @Column
    private String categoryName;

    @Enumerated(EnumType.STRING)
    @Column
    private Difficulty difficulty;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    @OneToMany(
            mappedBy = "gameSession",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<QuestionHistory> questionHistory = new ArrayList<>();

    @OneToMany(
            mappedBy = "gameSession",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<GameQuestion> questions = new ArrayList<>();
}
