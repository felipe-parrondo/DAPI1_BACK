package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.entity.embeddable.IngredientEmbeddable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEmbeddable, Long> {
    Optional<IngredientEmbeddable> findByName(String name);
}
