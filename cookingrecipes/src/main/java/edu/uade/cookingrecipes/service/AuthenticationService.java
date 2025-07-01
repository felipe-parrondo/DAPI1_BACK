package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.auth.AuthenticationRequestDto;
import edu.uade.cookingrecipes.dto.auth.AuthenticationResponseDto;
import edu.uade.cookingrecipes.dto.auth.ChangePasswordRequestDto;
import edu.uade.cookingrecipes.dto.auth.CreateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.RegisterRequestDto;
import edu.uade.cookingrecipes.dto.auth.UserSuggestionResponseDto;
import edu.uade.cookingrecipes.dto.auth.ValidateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.ValidateRegisterRequestDto;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import org.springframework.web.multipart.MultipartFile;

public interface AuthenticationService {

    AuthenticationResponseDto authenticate(AuthenticationRequestDto authRequest);

    AuthenticationResponseDto register(RegisterRequestDto registerRequest, MultipartFile avatar, MultipartFile dniFront, MultipartFile dniBack);

    UserSuggestionResponseDto validateRegister(ValidateRegisterRequestDto validateRegisterRequest);

    void createCode(CreateCodeRequestDto createCodeRequest);

    void validateCode(ValidateCodeRequestDto validateCodeRequest);

    void changePassword(ChangePasswordRequestDto changePasswordRequest);

    void createTestUser();

    void deleteAuthenticationByUserModel(UserModel userModel);
}
