package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.entity.AccountMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMovementRepository extends JpaRepository<AccountMovement, Long>{
    List<AccountMovement> findByUserId(Long userId);
}
