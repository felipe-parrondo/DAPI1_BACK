package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.model.AuthenticationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationModel, Long> {
    Optional<AuthenticationModel> findByUsername(String username);
    Optional<AuthenticationModel> findByEmail(String email);
}