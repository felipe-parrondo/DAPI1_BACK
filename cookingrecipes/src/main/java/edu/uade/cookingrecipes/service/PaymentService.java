package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.response.BalanceMovementsResponseDto;

public interface PaymentService {
    BalanceMovementsResponseDto getBalanceMovements();
}
