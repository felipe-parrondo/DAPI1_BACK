package edu.uade.cookingrecipes.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uade.cookingrecipes.dto.auth.AuthenticationRequestDto;
import edu.uade.cookingrecipes.dto.auth.AuthenticationResponseDto;
import edu.uade.cookingrecipes.dto.auth.ChangePasswordRequestDto;
import edu.uade.cookingrecipes.dto.auth.CreateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.RegisterRequestDto;
import edu.uade.cookingrecipes.dto.auth.ValidateCodeRequestDto;
import edu.uade.cookingrecipes.dto.auth.ValidateRegisterRequestDto;
import edu.uade.cookingrecipes.service.AuthenticationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "Authentication Operations")
@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping(path = "/testuser")
    public ResponseEntity<Void> testUserCreation () {
        authenticationService.createTestUser();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/authenticate",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDto> authenticate (@Valid @RequestBody
                                                                   @NotNull(message = "{authentication-controller.authenticate-service.authorization-request-not-null}")
                                                                   AuthenticationRequestDto authRequest) {
        logger.info(authRequest.toString());
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }

    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestPart(value = "user") String userJson,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar,
            @RequestPart(value = "front", required = false) MultipartFile front,
            @RequestPart(value = "back", required = false) MultipartFile back
    ) {
        try {
            logger.info(avatar.toString());
            logger.info(front.toString());
            logger.info(back.toString());
            logger.info(userJson);
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterRequestDto registerRequest = objectMapper.readValue(userJson, RegisterRequestDto.class);

            AuthenticationResponseDto response = authenticationService.register(registerRequest, avatar, front, back);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.info(e.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> validateRegister (@Valid @RequestBody
                                                  @NotNull(message = "{authentication-controller.register-service.validate-code-request-not-null}")
                                                  ValidateRegisterRequestDto validateRegisterRequest) {
        logger.info(validateRegisterRequest.toString());
        authenticationService.validateRegister(validateRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/code/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCode (@Valid @RequestBody
                                            @NotNull(message = "{authentication-controller.register-service.create-code-request-not-null}")
                                            CreateCodeRequestDto createCodeRequest) {
        logger.info(createCodeRequest.toString());
        authenticationService.createCode(createCodeRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/code/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> validateCode (@Valid @RequestBody
                                              @NotNull(message = "{authentication-controller.register-service.validate-code-request-not-null}")
                                              ValidateCodeRequestDto validateCodeRequest) {
        logger.info(validateCodeRequest.toString());
        authenticationService.validateCode(validateCodeRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/password/change", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changePassword (@Valid @RequestBody
                                                @NotNull(message = "{authentication-controller.register-service.change-password-request-not-null}")
                                                ChangePasswordRequestDto changePasswordRequest) {
        logger.info(changePasswordRequest.toString());
        authenticationService.changePassword(changePasswordRequest);
        return ResponseEntity.ok().build();
    }
}