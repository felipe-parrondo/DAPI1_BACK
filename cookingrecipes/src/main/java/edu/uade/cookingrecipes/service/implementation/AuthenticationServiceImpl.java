package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.config.JwtService;
import edu.uade.cookingrecipes.dto.auth.AuthenticationRequestDto;
import edu.uade.cookingrecipes.dto.auth.AuthenticationResponseDto;
import edu.uade.cookingrecipes.dto.auth.ChangePasswordRequestDto;
import edu.uade.cookingrecipes.dto.auth.CreateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.RegisterRequestDto;
import edu.uade.cookingrecipes.dto.auth.ValidateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.ValidateRegisterRequestDto;
import edu.uade.cookingrecipes.exceptions.EmailAlreadyInUseException;
import edu.uade.cookingrecipes.exceptions.EmailNotActivatedException;
import edu.uade.cookingrecipes.exceptions.UsernameAlreadyInUseException;
import edu.uade.cookingrecipes.mapper.AuthenticationMapper;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.CodeModel;
import edu.uade.cookingrecipes.model.RoleEnum;
import edu.uade.cookingrecipes.model.TempAuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.AuthenticationRepository;
import edu.uade.cookingrecipes.repository.CodeRepository;
import edu.uade.cookingrecipes.repository.TempAuthenticationRepository;
import edu.uade.cookingrecipes.repository.UserRepository;
import edu.uade.cookingrecipes.service.AuthenticationService;
import edu.uade.cookingrecipes.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CodeRepository codeRepository;

    private final TempAuthenticationRepository tempAuthRepository;

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final AuthenticationRepository authenticationRepository;
    private final AuthenticationMapper authenticationMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));

        AuthenticationModel authenticationModel = authenticationRepository.findByEmail(authRequest.email())
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
        TempAuthenticationModel tempAuthEmail = tempAuthRepository
                .findByEmail(validateCodeRequest.email())
                .orElse(null);
        if (Objects.nonNull(tempAuthEmail))
            throw new EmailNotActivatedException();

        AuthenticationModel authModelEmail = authenticationRepository
                .findByEmail(validateCodeRequest.email())
                .orElse(null);
        if (Objects.nonNull(authModelEmail))
            throw new EmailAlreadyInUseException();

        TempAuthenticationModel tempAuthUser = tempAuthRepository
                .findByUsername(validateCodeRequest.username())
                .orElse(null);
        AuthenticationModel authModelUser = authenticationRepository
                .findByUsername(validateCodeRequest.username())
                .orElse(null);
        if (Objects.nonNull(tempAuthUser) || Objects.nonNull(authModelUser)) {
            List<String> candidates = IntStream.range(1, 100)
                    .mapToObj(i -> validateCodeRequest.username() + i)
                    .toList();
            List<AuthenticationModel> authList = authenticationRepository.findByUsernameIn(candidates);
            List<TempAuthenticationModel> tempAuthList = tempAuthRepository.findByUsernameIn(candidates);
            Set<String> taken = new HashSet<>();
            taken.addAll(authList.stream().map(AuthenticationModel::getUsername).toList());
            taken.addAll(tempAuthList.stream().map(TempAuthenticationModel::getUsername).toList());
            candidates = candidates.stream()
                    .filter(u -> !taken.contains(u))
                    .toList();
            throw new UsernameAlreadyInUseException(candidates);
        }
    }

    @Override
    public void createCode(CreateCodeRequestDto createCodeRequest) {
        CodeModel codeModel = codeRepository.findByEmail(createCodeRequest.email()).orElse(new CodeModel());
        codeModel.setCode(Integer.toString((int)(Math.random() * 90000) + 10000));
        codeModel.setEmail(createCodeRequest.email());
        codeModel.setExpiration(LocalDateTime.now().plusMinutes(30));
        codeRepository.save(codeModel);
        emailService.sendEmail(createCodeRequest.email(), "Código de Verificación", codeModel.getCode());
    }

    @Override
    public void validateCode(ValidateCodeRequestDto validateCodeRequest) {
        CodeModel codeModel = codeRepository
                .findByEmail(validateCodeRequest.email())
                .orElseThrow(() -> new NoSuchElementException("user doesn't exist"));

        if (! (validateCodeRequest.code().equals(codeModel.getCode()) && codeModel.getExpiration().isAfter(LocalDateTime.now())) )
            throw new NoSuchElementException("invalid code");
    }

    @Override
    public void changePassword(ChangePasswordRequestDto changePasswordRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        AuthenticationModel authenticationModel = authenticationRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("user doesn't exist"));

        authenticationModel.setPassword(changePasswordRequest.password());
        authenticationRepository.save(authenticationModel);
    }

    @Override
    public void createTestUser() {
        UserModel userModel = new UserModel();
        userModel.setName("testero");
        userModel.setIsStudent(false);

        userModel = userRepository.save(userModel);

        AuthenticationModel authModel = new AuthenticationModel();
        authModel.setUsername("testero_user");
        authModel.setEmail("testero@testero.com");
        authModel.setRole(RoleEnum.ADMIN);
        authModel.setPassword(passwordEncoder.encode("12345"));
        authModel.setUser(userModel);

        authenticationRepository.save(authModel);
    }
}
