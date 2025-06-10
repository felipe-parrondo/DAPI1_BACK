package edu.uade.cookingrecipes.dto.auth;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.io.Serializable;

public record PaymentInformationDto (

        @JsonAlias("card_number")
        String cardNumber,

        String cvv,

        @JsonAlias("expiration_date")
        String expirationDate

) implements Serializable {}
