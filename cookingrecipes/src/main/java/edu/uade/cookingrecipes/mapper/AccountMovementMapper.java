package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.dto.response.AccountMovementResponseDto;
import edu.uade.cookingrecipes.entity.embeddable.AccountMovement;

public class AccountMovementMapper {

    public static AccountMovementResponseDto toDto (AccountMovement movement) {
        if (movement == null) return null;


        AccountMovementResponseDto dto = new AccountMovementResponseDto();
        dto.setId(movement.getId());
        dto.setDateTime(movement.getDateTime().toString());
        dto.setAmount(movement.getAmount());
        dto.setReason(movement.getReason());
        dto.setUserId(movement.getUser().getId());

        return dto;
    }

}
