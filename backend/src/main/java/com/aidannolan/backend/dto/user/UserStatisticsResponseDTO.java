package com.aidannolan.backend.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatisticsResponseDTO {
    private long gamesPlayed;

    private long gamesFinished;

    private long gamesWon;

    private long questionsAnswered;

    private long correctAnswers;

    private double accuracy;

    private double averageScore;

    private int highestScore;

    private double averageAccuracyPerGame;

    private String favoriteCategory;

    private String bestCategory;

    private long currentWinStreak;

    private long longestWinStreak;
}
