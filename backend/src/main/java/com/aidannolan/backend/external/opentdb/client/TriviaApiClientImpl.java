package com.aidannolan.backend.external.opentdb.client;

import com.aidannolan.backend.enums.Difficulty;
import com.aidannolan.backend.external.opentdb.dto.OpenTriviaQuestion;
import com.aidannolan.backend.external.opentdb.dto.OpenTriviaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TriviaApiClientImpl implements TriviaApiClient {
    private final WebClient openTriviaWebClient;

    private URI buildQuestionUri(int amount, Integer category, Difficulty difficulty) {
        return UriComponentsBuilder
                .fromPath("/api.php")
                .queryParam("amount", amount)
                .queryParamIfPresent("category", Optional.ofNullable(category))
                .queryParamIfPresent("difficulty", Optional.ofNullable(difficulty)
                        .map(d -> d.name().toLowerCase())
                )
                .build()
                .toUri();
    }

    @Override
    public List<OpenTriviaQuestion> getQuestions(int amount, Integer category, Difficulty difficulty) {
        OpenTriviaResponse response = openTriviaWebClient
                .get()
                .uri(buildQuestionUri(amount, category, difficulty))
                .retrieve()
                .bodyToMono(OpenTriviaResponse.class)
                .block();

        if (response == null) {
            throw new IllegalStateException("No response received from Open Trivia DB.");
        }

        validateResponse(response);

        return response.getResults();
    }

    private void validateResponse(OpenTriviaResponse response) {
        switch (response.getResponseCode()) {
            case 0:
                return;

            case 1:
                throw new IllegalStateException("No trivia questions were found.");

            case 2:
                throw new IllegalStateException("Invalid trivia request.");

            default:
                throw new IllegalStateException("Trivia API returned response code: " + response.getResponseCode());
        }
    }
}
