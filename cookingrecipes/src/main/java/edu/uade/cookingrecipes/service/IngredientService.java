package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.Entity.Embeddable.IngredientEmbeddable;

import java.util.List;

public interface IngredientService {
    void saveIfNotExists(String name);
    List<IngredientEmbeddable> getAllIngredients();
}
