package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.model.AuthenticationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationModel, Long> {

}
