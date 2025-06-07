package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.config.JwtService;
import edu.uade.cookingrecipes.dto.auth.AuthenticationRequestDto;
import edu.uade.cookingrecipes.dto.auth.AuthenticationResponseDto;
import edu.uade.cookingrecipes.dto.auth.ChangePasswordRequestDto;
import edu.uade.cookingrecipes.dto.auth.CreateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.CreateCodeResponseDto;
import edu.uade.cookingrecipes.dto.auth.RegisterRequestDto;
import edu.uade.cookingrecipes.dto.auth.ValidateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.ValidateCodeResponseDto;
import edu.uade.cookingrecipes.dto.auth.ValidateRegisterRequestDto;
import edu.uade.cookingrecipes.mapper.AuthenticationMapper;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.repository.AuthenticationRepository;
import edu.uade.cookingrecipes.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationRepository authenticationRepository;
    private final AuthenticationMapper authenticationMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

        AuthenticationModel authenticationModel = authenticationRepository.findByUsername(authRequest.username())
                .orElseThrow(() -> new NoSuchElementException("user doesn't exist"));

        return new AuthenticationResponseDto(jwtService.generateToken(authenticationModel));
    }

    @Override
    public AuthenticationResponseDto register(RegisterRequestDto registerRequest) {
        //registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        //UserModel userModel = userRepository.save(authenticationMapper.mapToDatabaseEntity(registerRequest));
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

    private void setPassword () {

    }
}
