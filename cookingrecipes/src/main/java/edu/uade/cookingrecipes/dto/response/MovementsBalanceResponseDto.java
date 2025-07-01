package edu.uade.cookingrecipes.dto.response;

import edu.uade.cookingrecipes.entity.embeddable.AccountMovement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementsBalanceResponseDto {
    private Double balance;
    private List<AccountMovement> movements;
}
