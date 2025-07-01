package edu.uade.cookingrecipes.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uade.cookingrecipes.dto.auth.PaymentInformationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private long id;
    private String name;
    private String username;
    private String email;
    private String address;
    @JsonProperty("isStudent")
    private boolean isStudent;
    @JsonProperty("paymentInformation")
    private PaymentInformationDto paymentInformation;
    @JsonProperty("accountBalance")
    private Double accountBalance;
}
