package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.dto.auth.AuthenticationRequestDto;
import edu.uade.cookingrecipes.dto.auth.AuthenticationResponseDto;
import edu.uade.cookingrecipes.dto.auth.ChangePasswordRequestDto;
import edu.uade.cookingrecipes.dto.auth.CreateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.CreateCodeResponseDto;
import edu.uade.cookingrecipes.dto.auth.RegisterRequestDto;
import edu.uade.cookingrecipes.dto.auth.ValidateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.ValidateCodeResponseDto;
import edu.uade.cookingrecipes.dto.auth.ValidateRegisterRequestDto;
import edu.uade.cookingrecipes.service.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authRequest) {
        return null; //TODO
    }

    @Override
    public AuthenticationResponseDto register(RegisterRequestDto registerRequest) {
        return null; //TODO
    }

    @Override
    public void validateRegister(ValidateRegisterRequestDto validateCodeRequest) {
        //TODO
    }

    @Override
    public CreateCodeResponseDto createCode(CreateCodeRequestDto createCodeRequest) {
        return null; //TODO
    }

    @Override
    public ValidateCodeResponseDto validateCode(ValidateCodeRequestDto validateCodeRequest) {
        return null; //TODO
    }

    @Override
    public void changePassword(ChangePasswordRequestDto changePasswordRequest) {
        //TODO
    }
}
