package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.Entity.Embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.Entity.Recipe;
import edu.uade.cookingrecipes.dto.Request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.Response.IngredientResponseDto;
import edu.uade.cookingrecipes.dto.Response.RecipeResponseDto;
import edu.uade.cookingrecipes.mapper.RecipeMapper;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.RecipeRepository;
import edu.uade.cookingrecipes.repository.UserRepository;
import edu.uade.cookingrecipes.service.IngredientService;
import edu.uade.cookingrecipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<RecipeResponseDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeResponseDto> filterRecipes(Specification<Recipe> spec, Pageable pageable) {
        Page<Recipe> result = recipeRepository.findAll(spec, pageable);
        return result.getContent().stream()
                .map(RecipeMapper::toDto)
                .toList();
    }

    @Override
    public RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto) {
        String address = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = userRepository.findByAddress(address);
        System.out.println(address);
        if (user == null) throw new NoSuchElementException("User not found: " + address);

        if (recipeRepository.existsByNameAndUser(recipeRequestDto.getName(), user)) {
            throw new IllegalArgumentException("Recipe with name '" +
                    recipeRequestDto.getName() + "' already exists for user: " + address);
        }

        Recipe recipe = RecipeMapper.toEntity(recipeRequestDto);
        recipe.setUser(user);

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
        return recipeRepository.findTop3ByOrderByIdDesc().stream()
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
    @Override
    public List<IngredientResponseDto> getFullIngredientsByRecipeId(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe == null || recipe.getIngredients() == null) return null;

        return recipe.getIngredients().stream().map(ingredient -> {
            IngredientResponseDto dto = new IngredientResponseDto();
            dto.setName(ingredient.getName());
            dto.setQuantity(ingredient.getQuantity());
            dto.setUnidad(ingredient.getUnidad());
            return dto;
        }).collect(Collectors.toList());
    }

}
