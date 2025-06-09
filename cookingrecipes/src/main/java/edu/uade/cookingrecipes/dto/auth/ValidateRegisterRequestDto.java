package edu.uade.cookingrecipes.dto.auth;

import java.io.Serializable;

public record ValidateRegisterRequestDto (

        String username,

        String email

) implements Serializable {}
