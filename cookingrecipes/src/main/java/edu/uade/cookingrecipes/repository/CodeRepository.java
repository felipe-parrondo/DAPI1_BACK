package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.model.CodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeModel, Long> {
    Optional<CodeModel> findByEmail(String email);
}
