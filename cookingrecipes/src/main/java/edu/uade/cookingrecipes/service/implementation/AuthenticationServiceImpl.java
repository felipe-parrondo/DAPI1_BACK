package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.config.JwtService;
import edu.uade.cookingrecipes.dto.auth.AuthenticationRequestDto;
import edu.uade.cookingrecipes.dto.auth.AuthenticationResponseDto;
import edu.uade.cookingrecipes.dto.auth.ChangePasswordRequestDto;
import edu.uade.cookingrecipes.dto.auth.CreateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.RegisterRequestDto;
import edu.uade.cookingrecipes.dto.auth.UserSuggestionResponseDto;
import edu.uade.cookingrecipes.dto.auth.ValidateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.ValidateRegisterRequestDto;
import edu.uade.cookingrecipes.exceptions.EmailAlreadyInUseException;
import edu.uade.cookingrecipes.exceptions.EmailNotActivatedException;
import edu.uade.cookingrecipes.exceptions.UsernameAlreadyInUseException;
import edu.uade.cookingrecipes.mapper.UserMapper;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.CodeModel;
import edu.uade.cookingrecipes.model.PaymentInformationModel;
import edu.uade.cookingrecipes.model.RoleEnum;
import edu.uade.cookingrecipes.model.TempAuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.AuthenticationRepository;
import edu.uade.cookingrecipes.repository.CodeRepository;
import edu.uade.cookingrecipes.repository.PaymentInformationRepository;
import edu.uade.cookingrecipes.repository.TempAuthenticationRepository;
import edu.uade.cookingrecipes.repository.UserRepository;
import edu.uade.cookingrecipes.service.AuthenticationService;
import edu.uade.cookingrecipes.service.EmailService;
import edu.uade.cookingrecipes.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final UserRepository userRepository;
    private final TempAuthenticationRepository tempAuthRepository;
    private final AuthenticationRepository authenticationRepository;
    private final PaymentInformationRepository paymentInformationRepository;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));

        AuthenticationModel authenticationModel = authenticationRepository.findByEmail(authRequest.email())
                .orElseThrow(() -> new NoSuchElementException("user doesn't exist"));

        return new AuthenticationResponseDto(
                jwtService.generateToken(authenticationModel),
                authenticationModel.getUser().getIsStudent(),
                "ADMIN".equals(authenticationModel.getRole().toString())
        );
    }

    public AuthenticationResponseDto register(RegisterRequestDto registerRequest, MultipartFile avatar, MultipartFile dniFront, MultipartFile dniBack) {
        PaymentInformationModel paymentInformationModel = null;

        if (registerRequest.isStudent()) {
            paymentInformationModel = UserMapper.registerRequestDtoToPaymentInformationModel(registerRequest);
            paymentInformationModel.setUrlFrontDNI(dniFront.getOriginalFilename());
            paymentInformationModel.setUrlBackDNI(dniBack.getOriginalFilename());
            paymentInformationModel = paymentInformationRepository.save(paymentInformationModel);
        }

        UserModel userModel = UserMapper.registerRequestDtoToUserModel(registerRequest);
        userModel.setAvatar(avatar.getOriginalFilename());
        AuthenticationModel authenticationModel = UserMapper.registerRequestDtoToAuthenticationModel(registerRequest);
        userModel.setPaymentInformationModel(paymentInformationModel);
        userModel = userRepository.save(userModel);
        authenticationModel.setUser(userModel);
        authenticationModel.setPassword(passwordEncoder.encode(authenticationModel.getPassword()));
        authenticationModel = authenticationRepository.save(authenticationModel);

        tempAuthRepository.delete(tempAuthRepository.findByEmail(registerRequest.email()).get());

        try {
            imageService.saveFile(avatar, userModel.getId().toString(), "users");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (registerRequest.isStudent()) {
            try {
                imageService.saveFile(dniBack, userModel.getId().toString(), "users");
                imageService.saveFile(dniFront, userModel.getId().toString(), "users");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.authenticate(new AuthenticationRequestDto(registerRequest.email(), registerRequest.password()));
    }

    @Override
    public UserSuggestionResponseDto validateRegister(ValidateRegisterRequestDto validateCodeRequest) {
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
            List<String> candidates = IntStream.range(1, 10)
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
            throw new UsernameAlreadyInUseException(candidates.subList(0, 3));
        }
        TempAuthenticationModel tempAuthSave = new TempAuthenticationModel();
        tempAuthSave.setEmail(validateCodeRequest.email());
        tempAuthSave.setUsername(validateCodeRequest.username());
        tempAuthRepository.save(tempAuthSave);
        return null;
    }

    @Override
    public void createCode(CreateCodeRequestDto createCodeRequest) {
        CodeModel codeModel = codeRepository.findByEmail(createCodeRequest.email()).orElse(new CodeModel());
        codeModel.setCode(Integer.toString((int)(Math.random() * 900000) + 100000));
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
        AuthenticationModel authenticationModel = authenticationRepository.findByEmail(changePasswordRequest.email())
                .orElseThrow(() -> new NoSuchElementException("user doesn't exist"));

        authenticationModel.setPassword(passwordEncoder.encode(changePasswordRequest.password()));
        authenticationRepository.save(authenticationModel);
    }

    @Override
    public void createTestUser() {
        PaymentInformationModel paymentInfo = new PaymentInformationModel();
        paymentInfo.setOwnerName("Carlos Perez");
        paymentInfo.setIsCredit(true);
        paymentInfo.setCardNumber("4111111111111111");
        paymentInfo.setCvv("123");
        paymentInfo.setExpirationDate("12/26");
        paymentInfo.setIdNumber("12345678");
        paymentInfo.setUrlBackDNI("blaBack");
        paymentInfo.setUrlFrontDNI("blaFront");

        paymentInfo = paymentInformationRepository.save(paymentInfo);

        UserModel userModel = new UserModel();
        userModel.setName("Carlos Perez");
        userModel.setUsername("testero");
        userModel.setAddress("Calle Falsa 123");
        userModel.setAvatar("media_recipe_temp_1751156847879.png");
        userModel.setIsStudent(true);
        userModel.setPaymentInformationModel(paymentInfo);
        userModel.setAccountBalance(0.0);

        userModel = userRepository.save(userModel);

        AuthenticationModel authModel = new AuthenticationModel();
        authModel.setUsername("testero");
        authModel.setEmail("user@user.com");
        authModel.setRole(RoleEnum.USER);
        authModel.setPassword(passwordEncoder.encode("12345"));
        authModel.setUser(userModel);

        authenticationRepository.save(authModel);
    }

    @Override
    public void deleteAuthenticationByUserModel(UserModel userModel) {
        AuthenticationModel authModel = authenticationRepository.findByUsername(userModel.getUsername())
                .orElseThrow(() -> new NoSuchElementException("invalid user id"));
        authModel.setPassword(passwordEncoder.encode("DESACTIVADO"));
        authenticationRepository.save(authModel);
    }
}
