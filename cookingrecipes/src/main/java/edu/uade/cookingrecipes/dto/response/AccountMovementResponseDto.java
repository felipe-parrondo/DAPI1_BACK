package edu.uade.cookingrecipes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountMovementResponseDto {
    private Long id;
    private String dateTime;
    private Double amount;
    private String reason;
    private Long userId;
}
