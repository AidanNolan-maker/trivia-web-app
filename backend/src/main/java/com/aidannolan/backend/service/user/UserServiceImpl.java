package com.aidannolan.backend.service.user;

import com.aidannolan.backend.dto.user.CurrentUserResponseDTO;
import com.aidannolan.backend.dto.user.UserStatisticsResponseDTO;
import com.aidannolan.backend.entity.GameQuestion;
import com.aidannolan.backend.entity.GameSession;
import com.aidannolan.backend.entity.User;
import com.aidannolan.backend.exception.ResourceNotFoundException;
import com.aidannolan.backend.mapper.UserMapper;
import com.aidannolan.backend.repository.GameSessionRepository;
import com.aidannolan.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final int POINTS_PER_CORRECT_ANSWER = 10;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final GameSessionRepository gameSessionRepository;

    @Override
    public CurrentUserResponseDTO getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        return userMapper.toCurrentUserResponseDTO(user);
    }

    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public UserStatisticsResponseDTO getStatistics() {
        User user = getCurrentUser();

        List<GameSession> games =
                gameSessionRepository.findByUserId(user.getId());

        long gamesPlayed = games.size();
        long gamesFinished = games.stream()
                .filter(game -> game.getFinishedAt() != null)
                .count();

        long questionsAnswered = games.stream()
                .flatMap(game -> game.getQuestions().stream())
                .filter(GameQuestion::isAnswered)
                .count();

        long correctAnswers = games.stream()
                .flatMap(game -> game.getQuestions().stream())
                .filter(GameQuestion::isCorrect)
                .count();

        double accuracy = questionsAnswered == 0
                ? 0
                : (correctAnswers * 100.0) / questionsAnswered;

        int highestScore = games.stream()
                .mapToInt(GameSession::getScore)
                .max()
                .orElse(0);

        double averageScore = games.stream()
                .mapToInt(GameSession::getScore)
                .average()
                .orElse(0);

        long gamesWon = games.stream()
                .filter(this::isWin)
                .count();

        double averageAccuracyPerGame = games.stream()
                .filter(game -> !game.getQuestions().isEmpty())
                .mapToDouble(game -> {
                    long answered = game.getQuestions().stream()
                            .filter(GameQuestion::isAnswered)
                            .count();

                    if (answered == 0) {
                        return 0;
                    }

                    long correct = game.getQuestions().stream()
                            .filter(GameQuestion::isCorrect)
                            .count();

                    return (correct * 100.0) / answered;
                })
                .average()
                .orElse(0);

        String favoriteCategory = games.stream()
                .filter(game -> game.getCategoryName() != null)
                .collect(Collectors.groupingBy(
                        GameSession::getCategoryName,
                        Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        String bestCategory = games.stream()
                .filter(game -> game.getCategoryName() != null)
                .collect(Collectors.groupingBy(GameSession::getCategoryName))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > 1)
                .max(Comparator.comparingDouble(entry ->
                        entry.getValue().stream()
                                .mapToInt(GameSession::getScore)
                                .average()
                                .orElse(0)))
                .map(Map.Entry::getKey)
                .orElse("N/A");

        List<GameSession> finishedGames = games.stream()
                .filter(game -> game.getFinishedAt() != null)
                .sorted(Comparator.comparing(GameSession::getFinishedAt))
                .toList();

        long currentWinStreak = 0;

        for (int i = finishedGames.size() - 1; i >= 0; i--) {
            GameSession game = finishedGames.get(i);

            boolean won = isWin(game);

            if (!won) {
                break;
            }

            currentWinStreak++;
        }

        long longestWinStreak = 0;
        long streak = 0;

        for (GameSession game : finishedGames) {
            boolean won = isWin(game);

            if (won) {
                streak++;

                longestWinStreak =
                        Math.max(longestWinStreak, streak);
            } else {
                streak = 0;
            }
        }

        return UserStatisticsResponseDTO.builder()
                .gamesPlayed(gamesPlayed)
                .gamesFinished(gamesFinished)
                .gamesWon(gamesWon)
                .questionsAnswered(questionsAnswered)
                .correctAnswers(correctAnswers)
                .accuracy(accuracy)
                .averageScore(averageScore)
                .highestScore(highestScore)
                .build();
    }

    private boolean isWin(GameSession game) {
        return game.getScore()
                == game.getTotalQuestions()
                    * POINTS_PER_CORRECT_ANSWER;
    }
}
