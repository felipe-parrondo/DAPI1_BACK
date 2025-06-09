package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.Entity.Embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.Entity.Recipe;
import edu.uade.cookingrecipes.dto.Request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.Response.RecipeResponseDto;
import edu.uade.cookingrecipes.mapper.RecipeMapper;
import edu.uade.cookingrecipes.repository.RecipeRepository;
import edu.uade.cookingrecipes.service.IngredientService;
import edu.uade.cookingrecipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientService ingredientService;

    @Override
    public List<RecipeResponseDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto) {
        Recipe recipe = RecipeMapper.toEntity(recipeRequestDto);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return RecipeMapper.toDto(savedRecipe);
    }

    @Override
    public boolean approveRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe == null) return false;
        recipe.setApproved(true);
        recipeRepository.save(recipe);
        for (IngredientEmbeddable ingredient : recipe.getIngredients()) {
            ingredientService.saveIfNotExists(ingredient.getName());
        }
        return true;
    }

    @Override
    public List<RecipeResponseDto> getRecentRecipes() {
        return recipeRepository.findTop10ByOrderByIdDesc().stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteRecipe(Long recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            return false;
        }
        recipeRepository.deleteById(recipeId);
        return true;
    }

    @Override
    public RecipeResponseDto getRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .map(RecipeMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<String> getRecipeIngredients(Long recipeId) {
        List<IngredientEmbeddable> ingredients = recipeRepository.findById(recipeId)
                .map(Recipe::getIngredients)
                .orElse(null);
        if (ingredients == null) return null;
        return ingredients.stream()
                .map(IngredientEmbeddable::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRecipeDishTypes(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .map(recipe -> Collections.singletonList(recipe.getDishType()))
                .orElse(null);
    }
}
