package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.dto.auth.PaymentInformationDto;
import edu.uade.cookingrecipes.model.PaymentInformationModel;

public class PaymentInformationMapper {
    public static PaymentInformationModel toEntity (PaymentInformationDto dto) {
        if (dto == null) return null;

        PaymentInformationModel paymentInformation = new PaymentInformationModel();
        paymentInformation.setOwnerName(dto.ownerName());
        paymentInformation.setIsCredit(dto.isCredit());
        paymentInformation.setCardNumber(dto.cardNumber());
        paymentInformation.setCvv(dto.cvv());
        paymentInformation.setExpirationDate(dto.expirationDate());
        paymentInformation.setIdNumber(dto.idNumber());
        paymentInformation.setUrlBackDNI(dto.urlBackDNI());
        paymentInformation.setUrlFrontDNI(dto.urlFrontDNI());
        return paymentInformation;
    }
}
