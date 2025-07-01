package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.entity.Ingredient;
import edu.uade.cookingrecipes.repository.IngredientRepository;
import edu.uade.cookingrecipes.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public void saveIfNotExists(String name) {
        name = name.toLowerCase(Locale.ROOT).trim();
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        if(ingredientRepository.findByName(name).isEmpty())
            ingredientRepository.save(new Ingredient(null, name));
    }

    @Override
    public List<String> getAllIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(i -> i.getName().toLowerCase(Locale.ROOT))
                .map(i -> i.substring(0, 1).toUpperCase() + i.substring(1).toLowerCase())
                .collect(Collectors.toSet())
                .stream().toList();
    }
}