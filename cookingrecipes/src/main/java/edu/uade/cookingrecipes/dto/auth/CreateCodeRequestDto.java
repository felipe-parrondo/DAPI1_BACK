package edu.uade.cookingrecipes.dto.auth;

import java.io.Serializable;

public record CreateCodeRequestDto (

        String email

) implements Serializable {}
