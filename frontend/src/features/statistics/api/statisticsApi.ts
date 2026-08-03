import api from "../../../api/axios";

export interface UserStatistics {
    gamesPlayed: number;
    gamesFinished: number;
    gamesWon: number;
    questionsAnswered: number;
    correctAnswers: number;
    accuracy: number;
    highestScore: number;
    averageScore: number;
    averageAccuracyPerGame: number;
    bestCategory: string | null;
    favoriteCategory: string | null;
    currentWinStreak: number;
    longestWinStreak: number;
}

export async function getUserStatistics(): Promise<UserStatistics> {
    const response = await api.get<UserStatistics>(
        "/users/me/statistics"
    );

    return response.data;
}