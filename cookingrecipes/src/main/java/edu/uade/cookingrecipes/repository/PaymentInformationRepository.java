package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.model.PaymentInformationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformationModel, Long> {
}
