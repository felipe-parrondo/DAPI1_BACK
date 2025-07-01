package edu.uade.cookingrecipes.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record PaymentInformationDto (

        @JsonProperty("nameTitular")
        String ownerName,

        @JsonProperty("isCredit")
        Boolean isCredit,

        @JsonProperty("cardNumber")
        String cardNumber,

        @JsonProperty("cvv")
        String cvv,

        @JsonProperty("expirationDate")
        String expirationDate,

        @JsonProperty("numTramiteDNI")
        String idNumber,

        @JsonProperty("urlBackDNI")
        String urlBackDNI,

        @JsonProperty("urlFrontDNI")
        String urlFrontDNI
) implements Serializable {}
