package edu.uade.cookingrecipes.dto.auth;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.io.Serializable;
import java.util.List;

public record UserSuggestionResponseDto (

        @JsonAlias("user_suggestion")
        List<String> userSuggestion

) implements Serializable {}
