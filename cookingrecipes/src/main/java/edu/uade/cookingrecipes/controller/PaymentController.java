package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.dto.response.MovementsBalanceResponseDto;
import edu.uade.cookingrecipes.service.PaymentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Payment Operations")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/movements") // Ver movimientos del usuario logeado
    public ResponseEntity<MovementsBalanceResponseDto> getBalanceMovements() {
        MovementsBalanceResponseDto balanceMovements = paymentService.getBalanceMovements();
        if (balanceMovements != null) {
            return new ResponseEntity<>(balanceMovements, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/balance") // Ver balance del usuario logeado
    public ResponseEntity<Double> getBalance() {
        MovementsBalanceResponseDto balanceMovements = paymentService.getBalanceMovements();
        if (balanceMovements != null) {
            return new ResponseEntity<>(balanceMovements.getBalance(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
