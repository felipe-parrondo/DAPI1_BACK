package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.model.TempAuthenticationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TempAuthenticationRepository extends JpaRepository<TempAuthenticationModel, Long> {
    Optional<TempAuthenticationModel> findByUsername(String username);
    Optional<TempAuthenticationModel> findByEmail (String email);
    List<TempAuthenticationModel> findByUsernameIn (List<String> usernameList);
}
