package com.aidannolan.backend.external.opentdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class OpenTriviaResponse {
    @JsonProperty("response_code")
    private int responseCode;

    private List<OpenTriviaQuestion> results;
}
