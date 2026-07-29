package com.aidannolan.backend.service.game;

import com.aidannolan.backend.dto.game.*;
import com.aidannolan.backend.entity.GameQuestion;
import com.aidannolan.backend.entity.GameSession;
import com.aidannolan.backend.entity.User;
import com.aidannolan.backend.enums.Difficulty;
import com.aidannolan.backend.exception.ResourceNotFoundException;
import com.aidannolan.backend.external.opentdb.client.TriviaApiClient;
import com.aidannolan.backend.external.opentdb.dto.OpenTriviaCategory;
import com.aidannolan.backend.external.opentdb.dto.OpenTriviaQuestion;
import com.aidannolan.backend.repository.GameQuestionRepository;
import com.aidannolan.backend.repository.GameSessionRepository;
import com.aidannolan.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GameServiceImpl implements GameService {
    private static final int POINTS_PER_CORRECT_ANSWER = 10;

    private final GameSessionRepository gameSessionRepository;
    private final TriviaApiClient triviaApiClient;
    private final UserRepository userRepository;
    private final GameQuestionRepository gameQuestionRepository;

    @Override
    public GameSessionResponseDTO startGame(StartGameRequestDTO request) {
        User user = getCurrentUser();

        List<OpenTriviaQuestion> triviaQuestions =
                triviaApiClient.getQuestions(
                        request.getAmount(),
                        request.getCategory(),
                        request.getDifficulty()
                );

        GameSession gameSession = new GameSession();

        gameSession.setUser(user);
        gameSession.setCategoryId(request.getCategory());
        gameSession.setDifficulty(request.getDifficulty());
        gameSession.setScore(0);
        gameSession.setCorrectAnswers(0);
        gameSession.setTotalQuestions(triviaQuestions.size());
        gameSession.setStartedAt(LocalDateTime.now());

        if (request.getCategory() != null) {
            gameSession.setCategoryName(
                    getCategoryName(request.getCategory())
            );
        }

        List<GameQuestion> gameQuestions =
                triviaQuestions.stream()
                        .map(this::toGameQuestion)
                        .toList();

        gameQuestions.forEach(q -> q.setGameSession(gameSession));

        gameSession.setQuestions(gameQuestions);

        GameSession saved =
                gameSessionRepository.save(gameSession);

        return toResponse(saved);
    }

    private GameQuestion toGameQuestion(OpenTriviaQuestion question) {
        List<String> answers = new ArrayList<>();

        answers.add(decode(question.getCorrectAnswer()));

        question.getIncorrectAnswers()
                .stream()
                .map(this::decode)
                .forEach(answers::add);

        Collections.shuffle(answers);

        return GameQuestion.builder()
                .question(decode(question.getQuestion()))
                .category(decode(question.getCategory()))
                .difficulty(parseDifficulty(question.getDifficulty()))
                .type(question.getType())
                .correctAnswer(decode(question.getCorrectAnswer()))
                .answers(answers)
                .answered(false)
                .correct(false)
                .build();
    }

    private GameSessionResponseDTO toResponse(GameSession session) {
        return GameSessionResponseDTO.builder()
                .id(session.getId())
                .score(session.getScore())
                .correctAnswers(session.getCorrectAnswers())
                .totalQuestions(session.getTotalQuestions())
                .categoryId(session.getCategoryId())
                .categoryName(session.getCategoryName())
                .difficulty(session.getDifficulty())
                .startedAt(session.getStartedAt())
                .finishedAt(session.getFinishedAt())
                .questions(
                        session.getQuestions()
                                .stream()
                                .map(this::toQuestionResponse)
                                .toList()
                )
                .build();
    }

    private GameQuestionResponseDTO toQuestionResponse(GameQuestion question) {
        return GameQuestionResponseDTO.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .category(question.getCategory())
                .difficulty(question.getDifficulty())
                .type(question.getType())
                .answers(question.getAnswers())
                .answered(question.isAnswered())
                .selectedAnswer(question.getSelectedAnswer())
                .correct(question.isCorrect())
                .build();
    }

    private String decode(String text) {
        return StringEscapeUtils.unescapeHtml4(text);
    }

    private Difficulty parseDifficulty(String difficulty) {
        return Difficulty.valueOf(difficulty.toUpperCase());
    }

    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalStateException("Authenticated user not found."));
    }

    private String getCategoryName(Integer categoryId) {
        return triviaApiClient.getCategories()
                .stream()
                .filter(c -> c.getId().equals(categoryId))
                .findFirst()
                .map(OpenTriviaCategory::getName)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unknown category: " + categoryId
                        ));
    }

    @Override
    public SubmitAnswerResponseDTO submitAnswer(Long gameId, Long questionId, SubmitAnswerRequestDTO request) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        GameSession gameSession =
                gameSessionRepository.findByIdAndUserId(gameId, user.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Game not found."));

        GameQuestion question =
                gameQuestionRepository.findByIdAndGameSessionId(
                            questionId,
                            gameSession.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Question not found."));

        if (question.isAnswered()) {
            throw new IllegalStateException(
                    "This question has already been answered."
            );
        }

        boolean correct =
                question.getCorrectAnswer()
                        .equals(request.getSelectedAnswer());

        question.setAnswered(true);
        question.setSelectedAnswer(request.getSelectedAnswer());
        question.setCorrect(correct);

        if (correct) {
            gameSession.setCorrectAnswers(
                    gameSession.getCorrectAnswers() + 1
            );

            gameSession.setScore(
                    gameSession.getScore() + POINTS_PER_CORRECT_ANSWER
            );
        }

        int answeredQuestions =
                (int) gameSession.getQuestions()
                        .stream()
                        .filter(GameQuestion::isAnswered)
                        .count();

        boolean gameFinished =
                answeredQuestions == gameSession.getTotalQuestions();

        if (gameFinished) {
            gameSession.setFinishedAt(LocalDateTime.now());
        }

        gameQuestionRepository.save(question);
        gameSessionRepository.save(gameSession);

        return SubmitAnswerResponseDTO.builder()
                .correct(correct)
                .score(gameSession.getScore())
                .correctAnswers(gameSession.getCorrectAnswers())
                .questionsAnswered(answeredQuestions)
                .totalQuestions(gameSession.getTotalQuestions())
                .gameFinished(gameFinished)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GameSessionResponseDTO getGame(Long gameId) {
        User user = getCurrentUser();

        GameSession gameSession = gameSessionRepository
                .findByIdAndUserId(gameId, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Game not found."));

        return toResponse(gameSession);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameSummaryResponseDTO> getGames(Boolean finished, Difficulty difficulty, Integer categoryId, Pageable pageable) {
        User user = getCurrentUser();

        Page<GameSession> games;

        if (finished != null) {
            if (finished) {
                games = gameSessionRepository.findByUserIdAndFinishedAtIsNotNull(user.getId(), pageable);
            } else {
                games = gameSessionRepository.findByUserIdAndFinishedAtIsNull(user.getId(), pageable);
            }
        } else if (difficulty != null) {
            games = gameSessionRepository.findByUserIdAndDifficulty(user.getId(), difficulty, pageable);
        } else if (categoryId != null) {
            games = gameSessionRepository.findByUserIdAndCategoryId(user.getId(), categoryId, pageable);
        } else {
            games = gameSessionRepository.findByUserId(user.getId(), pageable);
        }

        return games.map(this::toSummaryResponse);
    }

    private GameSummaryResponseDTO toSummaryResponse(GameSession gameSession) {
        return GameSummaryResponseDTO.builder()
                .id(gameSession.getId())
                .score(gameSession.getScore())
                .correctAnswers(gameSession.getCorrectAnswers())
                .totalQuestions(gameSession.getTotalQuestions())
                .categoryId(gameSession.getCategoryId())
                .categoryName(gameSession.getCategoryName())
                .difficulty(gameSession.getDifficulty())
                .startedAt(gameSession.getStartedAt())
                .finishedAt(gameSession.getFinishedAt())
                .build();
    }
}
