package edu.uade.cookingrecipes.dto.auth;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record ChangePasswordRequestDto (

        @NotBlank(message = "{change-password-dto.password-not-blank}")
        String password

) implements Serializable {}
