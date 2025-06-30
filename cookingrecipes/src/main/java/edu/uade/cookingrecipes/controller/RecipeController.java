package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.dto.response.IngredientResponseDto;
import edu.uade.cookingrecipes.entity.embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.dto.request.RatingRequestDto;
import edu.uade.cookingrecipes.dto.request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.response.RatingResponseDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;
import edu.uade.cookingrecipes.service.IngredientService;
import edu.uade.cookingrecipes.service.RatingService;
import edu.uade.cookingrecipes.service.RecipeService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api (value = "Recipe Operations")
@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private IngredientService ingredientService;

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);


    @GetMapping("/") //Obtener todas las recetas del usuario que esta logueado
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
        List<RecipeResponseDto> filteredRecipes = recipeService.filterRecipes(dishType, order, ingredient,
                sortByDate, username);
        return new ResponseEntity<>(filteredRecipes, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //Crear receta
    public ResponseEntity<RecipeResponseDto> createRecipe(@RequestPart("recipe") RecipeRequestDto recipeRequestDto,
                                                          @RequestPart(value = "mediaFiles", required = false) List<MultipartFile> files) {
        RecipeResponseDto createdRecipe = recipeService.createRecipe(recipeRequestDto, files);
        if (createdRecipe != null) {
            return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/validate") //Validar receta
    public ResponseEntity<Long> validateRecipe(@RequestBody String recipeName) {
        Long userId = recipeService.validateRecipe(recipeName);
        if (userId != null) {
            return new ResponseEntity<>(userId, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/rating") //Valorar receta
    public ResponseEntity<RatingResponseDto> ratingRecipe(@RequestBody RatingRequestDto ratingRequestDto) {
        RatingResponseDto ratedRecipe = ratingService.ratingRecipe(ratingRequestDto);
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
        logger.info("REQUESTING RECENT RECIPES");
        List<RecipeResponseDto> recentRecipes = recipeService.getRecentRecipes();
        logger.info(recentRecipes.toString());
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

    @GetMapping("/ingredient") //Obtener todas los ingredientes
    public ResponseEntity<List<IngredientEmbeddable>> getAllIngredients() {
        List<IngredientEmbeddable> ingredients = ingredientService.getAllIngredients();
        if (ingredients != null) {
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/dishType") //Obtener todos los tipos de platos
    public ResponseEntity<List<String>> getAllDishTypes() {
        List<String> dishTypes = recipeService.getAllDishTypes();
        if (dishTypes != null) {
            return new ResponseEntity<>(dishTypes, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{recipeId}/full-ingredients")
    public ResponseEntity<List<IngredientResponseDto>> getFullRecipeIngredients(@PathVariable Long recipeId) {
        List<IngredientResponseDto> ingredients = recipeService.getFullIngredientsByRecipeId(recipeId);
        if (ingredients != null) {
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
