package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.Entity.Ingredient;
import edu.uade.cookingrecipes.repository.IngredientRepository;
import edu.uade.cookingrecipes.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public void saveIfNotExists(String name) {
        ingredientRepository.findByName(name.toLowerCase().trim())
                .orElseGet(() -> ingredientRepository.save(
                        Ingredient.builder().name(name.toLowerCase().trim()).build()));
    }
}
