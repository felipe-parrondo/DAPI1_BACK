package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.Entity.Embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.dto.Request.RatingRequestDto;
import edu.uade.cookingrecipes.dto.Request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.Response.RatingResponseDto;
import edu.uade.cookingrecipes.dto.Response.RecipeResponseDto;
import edu.uade.cookingrecipes.service.RatingService;
import edu.uade.cookingrecipes.service.RecipeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api (value = "Recipe Operations")
@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RatingService ratingService;


    @GetMapping("/")
    public ResponseEntity<List<RecipeResponseDto>> getAllRecipes() {
        List<RecipeResponseDto> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/filter") //Obtener recetas filtradas
    public ResponseEntity<List<RecipeResponseDto>> filterRecipes(
            @RequestParam(required = false) String dishType,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) String ingredient,
            @RequestParam(required = false) String sortByDate,
            @RequestParam(required = false) String username
    ) {
        List<RecipeResponseDto> filteredRecipes = recipeService.filterRecipes(dishType, order, ingredient, sortByDate, username);
        return new ResponseEntity<>(filteredRecipes, HttpStatus.OK);
    }

    @PostMapping("/create") //Crear receta
    public ResponseEntity<RecipeResponseDto> createRecipe(@RequestBody RecipeRequestDto recipeRequestDto) {
        RecipeResponseDto createdRecipe = recipeService.createRecipe(recipeRequestDto);
        if (createdRecipe != null) {
            return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{recipeId}/rating") //Valorar receta
    public ResponseEntity<RecipeResponseDto> ratingRecipe(@PathVariable Long recipeId,
                                                          @RequestBody RatingRequestDto ratingRequestDto) {
        RecipeResponseDto ratedRecipe = ratingService.ratingRecipe(recipeId, ratingRequestDto);
        if (ratedRecipe != null) {
            return new ResponseEntity<>(ratedRecipe, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/rating/{ratingId}/cr") //Aprobar/rechazar valoracion
    public ResponseEntity<Void> reviewRatingRecipe(@PathVariable Long ratingId) {
        boolean ratedRecipe = ratingService.approveRating(ratingId);
        if (ratedRecipe) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{recipeId}/cr") //Aprobar/rechazar receta
    public ResponseEntity<Void> approveRecipe(@PathVariable Long recipeId) {
        boolean approvedRecipe = recipeService.approveRecipe(recipeId);
        if (approvedRecipe) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/recent") //Obtener recetas recientes
    public ResponseEntity<List<RecipeResponseDto>> getRecentRecipes() {
        List<RecipeResponseDto> recentRecipes = recipeService.getRecentRecipes();
        return new ResponseEntity<>(recentRecipes, HttpStatus.OK);
    }

    @DeleteMapping("/{recipeId}") //Eliminar receta
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        boolean isDeleted = recipeService.deleteRecipe(recipeId);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/rating/{ratingId}") //Eliminar valoracion de receta
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        boolean isDeleted = ratingService.deleteRating(ratingId);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{recipeId}") //Obtener receta por ID
    public ResponseEntity<RecipeResponseDto> getRecipeById(@PathVariable Long recipeId) {
        RecipeResponseDto recipe = recipeService.getRecipeById(recipeId);
        if (recipe != null) {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/rating/{ratingId}/update") //Actualizar valoracion de receta
    public ResponseEntity<RatingResponseDto> updateRating(@PathVariable Long ratingId,
                                                                @RequestBody RatingRequestDto ratingRequestDto) {
        RatingResponseDto updatedRating = ratingService.updateRating(ratingId, ratingRequestDto);
        if (updatedRating != null) {
            return new ResponseEntity<>(updatedRating, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/rating/{recipeId}") //Obtener todas las valoraciones de una receta
    public ResponseEntity<List<RatingResponseDto>> getRatingsByRecipeId(@PathVariable Long recipeId) {
        List<RatingResponseDto> recipeRatings = ratingService.getRatingsByRecipeId(recipeId);
        return new ResponseEntity<>(recipeRatings, HttpStatus.OK);
    }

    @GetMapping("/ingredients/{recipeId}") //Obtener ingredientes de una receta
    public ResponseEntity<List<String>> getRecipeIngredients(@PathVariable Long recipeId) {
        List<String> ingredients = recipeService.getRecipeIngredients(recipeId);
        if (ingredients != null) {
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/dishType/{recipeId}") //Obtener tipos de platos de una receta
    public ResponseEntity<List<String>> getRecipeDishTypes(@PathVariable Long recipeId) {
        List<String> dishTypes = recipeService.getRecipeDishTypes(recipeId);
        if (dishTypes != null) {
            return new ResponseEntity<>(dishTypes, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
