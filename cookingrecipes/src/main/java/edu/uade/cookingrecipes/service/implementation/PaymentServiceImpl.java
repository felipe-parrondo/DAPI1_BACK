package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.dto.response.MovementsBalanceResponseDto;
import edu.uade.cookingrecipes.entity.embeddable.AccountMovement;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.AuthenticationRepository;
import edu.uade.cookingrecipes.repository.AccountMovementRepository;
import edu.uade.cookingrecipes.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private AccountMovementRepository paymentMovementsRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public MovementsBalanceResponseDto getBalanceMovements() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = authenticationRepository.findByEmail(email)
                .map(AuthenticationModel::getUser)
                .orElse(null);
        if (user == null) throw new NoSuchElementException("User not found");

        List<AccountMovement> movements = paymentMovementsRepository.findByUserId(user.getId());

        return new MovementsBalanceResponseDto(
                user.getAccountBalance(),
                movements
        );
    }
}
