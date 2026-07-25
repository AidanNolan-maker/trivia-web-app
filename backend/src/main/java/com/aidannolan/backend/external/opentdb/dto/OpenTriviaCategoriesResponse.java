package com.aidannolan.backend.external.opentdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpenTriviaCategoriesResponse {
    @JsonProperty("trivia_categories")
    private List<OpenTriviaCategory> triviaCategories;
}
