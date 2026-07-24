package com.aidannolan.backend.exception;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiError {
    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;

    @Builder.Default
    private Map<String, String> fieldErrors = new HashMap<>();
}
