package edu.uade.cookingrecipes.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record GetStudentUserResponseDto (

        String username,
        String email,
        String avatar,
        Long id,
        @JsonProperty("frontDni")
        String frontDni,
        @JsonProperty("backDni")
        String backDni,
        @JsonProperty("cardNumber")
        String cardNumber,
        @JsonProperty("isStudent")
        Boolean isStudent

) implements Serializable {}