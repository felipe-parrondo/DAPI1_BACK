package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.dto.response.UserResponseDto;
import edu.uade.cookingrecipes.dto.auth.RegisterRequestDto;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.PaymentInformationModel;
import edu.uade.cookingrecipes.model.UserModel;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public static UserResponseDto toDto(UserModel user, String email) {
        if (user == null) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(email);
        userResponseDto.setAddress(user.getAddress());
        userResponseDto.setStudent(user.getIsStudent());
        if (user.getPaymentInformationModel() != null) {
            userResponseDto.setPaymentInformation(user.getPaymentInformationModel().toString());
        } else {
            userResponseDto.setPaymentInformation(null);
        }
        userResponseDto.setAccountBalance(user.getAccountBalance());

        return userResponseDto;
    }
}
