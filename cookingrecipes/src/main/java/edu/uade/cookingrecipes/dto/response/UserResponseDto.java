package edu.uade.cookingrecipes.dto.response;

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
    private boolean isStudent;
    private String paymentInformation; //TODO devolver el objeto
    private Double accountBalance;
    //TODO agregar toda la clase excepto password
}
