package edu.uade.cookingrecipes.dto.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uade.cookingrecipes.model.RoleEnum;

import java.io.Serializable;

public record RegisterRequestDto (

    String password,

    String name,

    String username,

    String email,

    String address,

    @JsonProperty("isStudent")
    Boolean isStudent,

    RoleEnum role,

    @JsonProperty("paymentInformation")
    PaymentInformationDto paymentInformation

) implements Serializable {}
