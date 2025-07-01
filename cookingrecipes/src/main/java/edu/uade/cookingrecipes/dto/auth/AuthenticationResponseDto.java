package edu.uade.cookingrecipes.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record AuthenticationResponseDto (

        String token,

        @JsonProperty("isStudent")
        Boolean isStudent,
        @JsonProperty("isAdmin")
        Boolean isAdmin

) implements Serializable {}