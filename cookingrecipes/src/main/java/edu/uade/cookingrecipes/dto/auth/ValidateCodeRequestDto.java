package edu.uade.cookingrecipes.dto.auth;

import java.io.Serializable;

public record ValidateCodeRequestDto (

        String email,

        String code

) implements Serializable {}
