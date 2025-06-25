package edu.uade.cookingrecipes.dto.auth;

import java.io.Serializable;

public record AuthenticationResponseDto (

        String token,

        Boolean isStudent

) implements Serializable {}