package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.entity.DishTypes;
import edu.uade.cookingrecipes.entity.embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.entity.Recipe;
import edu.uade.cookingrecipes.dto.request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;
import edu.uade.cookingrecipes.mapper.RecipeMapper;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.AuthenticationRepository;
import edu.uade.cookingrecipes.repository.RecipeRepository;
import edu.uade.cookingrecipes.repository.UserRepository;
import edu.uade.cookingrecipes.service.IngredientService;
import edu.uade.cookingrecipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    private AuthenticationRepository authenticationRepository;

    @Override
    public List<RecipeResponseDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeResponseDto> filterRecipes(String dishType, String order, String ingredient,
                                                 String sortByDate, String username) {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream()
                .filter(r -> dishType == null || r.getDishType().equalsIgnoreCase(dishType))
                .filter(r -> username == null || r.getUser().getName().equalsIgnoreCase(username))
                .filter(r -> ingredient == null || r.getIngredients().stream()
                        .anyMatch(i -> i.getName().equalsIgnoreCase(ingredient)))
                .sorted((r1, r2) -> {
                    if ("asc".equalsIgnoreCase(order)) {
                        return r1.getName().compareToIgnoreCase(r2.getName());
                    } else if ("desc".equalsIgnoreCase(order)) {
                        return r2.getName().compareToIgnoreCase(r1.getName());
                    } else if ("newest".equalsIgnoreCase(sortByDate)) {
                        return r2.getId().compareTo(r1.getId());
                    } else if ("oldest".equalsIgnoreCase(sortByDate)) {
                        return r1.getId().compareTo(r2.getId());
                    }
                    return 0;
                })
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = authenticationRepository.findByEmail(email)
                .map(AuthenticationModel::getUser)
                .orElse(null);
        if (user == null) throw new NoSuchElementException("User not found: " + email);

        if (recipeRepository.existsByNameAndUser(recipeRequestDto.getName(), user)) {
            throw new IllegalArgumentException("Recipe with name '" +
                    recipeRequestDto.getName() + "' already exists for user: " + email);
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
    public List<String> getAllDishTypes() {
        return Arrays.stream(DishTypes.values())
                .map(DishTypes::name)
                .toList();
    }
}
