package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.dto.auth.RegisterRequestDto;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.PaymentInformationModel;
import edu.uade.cookingrecipes.model.UserModel;

public class UserMapper {

    public static AuthenticationModel registerRequestDtoToAuthenticationModel (RegisterRequestDto registerRequest) {
        AuthenticationModel authenticationModel = new AuthenticationModel();
        authenticationModel.setPassword(registerRequest.password());
        authenticationModel.setRole(registerRequest.role());
        authenticationModel.setEmail(registerRequest.email());
        authenticationModel.setUsername(registerRequest.username());
        authenticationModel.setUser(UserMapper.registerRequestDtoToUserModel(registerRequest));
        return authenticationModel;
    }

    public static UserModel registerRequestDtoToUserModel (RegisterRequestDto registerRequest) {
        UserModel userModel = new UserModel();
        userModel.setIsStudent(registerRequest.isStudent());
        userModel.setName(registerRequest.name());
        userModel.setPaymentInformationModel(UserMapper.registerRequestDtoToPaymentInformationModel(registerRequest));
        return userModel;
    }

    public static PaymentInformationModel registerRequestDtoToPaymentInformationModel (RegisterRequestDto registerRequest) {
        PaymentInformationModel paymentInformationModel = new PaymentInformationModel();
        paymentInformationModel.setCvv(registerRequest.paymentInformation().cvv());
        paymentInformationModel.setCardNumber(registerRequest.paymentInformation().cardNumber());
        paymentInformationModel.setExpirationDate(registerRequest.paymentInformation().expirationDate());
        return paymentInformationModel;
    }
}
