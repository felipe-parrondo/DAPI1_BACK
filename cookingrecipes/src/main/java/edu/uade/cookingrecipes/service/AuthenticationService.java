package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.auth.AuthenticationRequestDto;
import edu.uade.cookingrecipes.dto.auth.AuthenticationResponseDto;
import edu.uade.cookingrecipes.dto.auth.RegisterRequestDto;

public interface AuthenticationService {

    AuthenticationResponseDto authenticate(AuthenticationRequestDto authRequest);

    AuthenticationResponseDto register(RegisterRequestDto registerRequest);
}
