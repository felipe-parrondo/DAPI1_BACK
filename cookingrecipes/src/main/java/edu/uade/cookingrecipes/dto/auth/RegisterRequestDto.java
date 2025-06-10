package edu.uade.cookingrecipes.dto.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import edu.uade.cookingrecipes.model.RoleEnum;

import java.io.Serializable;

public record RegisterRequestDto (

    String password,

    String name,

    String username,

    String email,

    @JsonAlias("is_student")
    Boolean isStudent,

    RoleEnum role,

    @JsonAlias("payment_information")
    PaymentInformationDto paymentInformation

) implements Serializable {}
