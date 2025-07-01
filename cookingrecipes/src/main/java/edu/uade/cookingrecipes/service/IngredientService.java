package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.entity.embeddable.IngredientEmbeddable;

import java.util.List;

public interface IngredientService {
    List<String> getAllIngredients();
}