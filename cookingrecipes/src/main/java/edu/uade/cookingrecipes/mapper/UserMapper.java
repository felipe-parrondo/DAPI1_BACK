package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.dto.Response.UserResponseDto;
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
        userModel.setAddress(registerRequest.address());
        if (registerRequest.isStudent())
            userModel.setPaymentInformationModel(UserMapper.registerRequestDtoToPaymentInformationModel(registerRequest));
        else
            userModel.setPaymentInformationModel(null);
        return userModel;
    }

    public static PaymentInformationModel registerRequestDtoToPaymentInformationModel (RegisterRequestDto registerRequest) {
        PaymentInformationModel paymentInformationModel = new PaymentInformationModel();
        paymentInformationModel.setCvv(registerRequest.paymentInformation().cvv());
        paymentInformationModel.setCardNumber(registerRequest.paymentInformation().cardNumber());
        paymentInformationModel.setExpirationDate(registerRequest.paymentInformation().expirationDate());
        paymentInformationModel.setIdNumber(registerRequest.paymentInformation().idNumber());
        paymentInformationModel.setIsCredit(registerRequest.paymentInformation().isCredit());
        paymentInformationModel.setOwnerName(registerRequest.paymentInformation().ownerName());
        return paymentInformationModel;
    }

    public static UserResponseDto toDto(UserModel user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getAddress(),
                user.getIsStudent()
        );
    }
}
