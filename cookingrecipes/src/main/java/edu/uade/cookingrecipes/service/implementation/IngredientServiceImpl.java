package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.entity.embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.repository.IngredientRepository;
import edu.uade.cookingrecipes.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public List<String> getAllIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(IngredientEmbeddable::getName)
                .toList();
    }

}