package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.entity.Recipe;
import edu.uade.cookingrecipes.dto.response.IngredientResponseDto;
import edu.uade.cookingrecipes.entity.embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.dto.request.RatingRequestDto;
import edu.uade.cookingrecipes.dto.request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.response.RatingResponseDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;
import edu.uade.cookingrecipes.service.IngredientService;
import edu.uade.cookingrecipes.service.RatingService;
import edu.uade.cookingrecipes.service.RecipeService;
import edu.uade.cookingrecipes.specification.RecipeSpecification;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

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
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String hasIngredient,
            @RequestParam(required = false) String hasntIngredient,
            @RequestParam(required = false) String user,
            @RequestParam(required = false) Integer approved, //0: desaprobado, 1: aprobado, 2: pendientes
            @RequestParam(required = false, defaultValue = "") String sort //default=dish, other values: user, recent. Make a default value if the string isn't in this 3 defined
    ) {
        Specification<Recipe> spec = RecipeSpecification.withFilters(name, type, hasIngredient, hasntIngredient, user, approved);
        return ResponseEntity.ok(recipeService.filterRecipes(spec, sort));
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
    public ResponseEntity<RatingResponseDto> ratingRecipe( @RequestBody RatingRequestDto ratingRequestDto) {
        RatingResponseDto rating = ratingService.ratingRecipe(ratingRequestDto.getRecipeId(), ratingRequestDto);
        if (rating != null) {
            return new ResponseEntity<>(rating, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/rating/{ratingId}/cr/{isApproved}") //Aprobar/rechazar valoracion
    public ResponseEntity<Void> reviewRatingRecipe(@PathVariable Long ratingId, Boolean isApproved) {
        boolean ratedRecipe = ratingService.approveRating(ratingId, isApproved);
        if (ratedRecipe) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{recipeId}/cr/{isApproved}") //Aprobar/rechazar receta
    public ResponseEntity<Void> approveRecipe(@PathVariable Long recipeId, Boolean isApproved) {
        boolean approvedRecipe = recipeService.approveRecipe(recipeId, isApproved);
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

    @GetMapping("/rating/recipe/{recipeId}") //Obtener todas las valoraciones de una receta
    public ResponseEntity<List<RatingResponseDto>> getRatingsByRecipeId(@PathVariable Long recipeId) {
        List<RatingResponseDto> recipeRatings = ratingService.getRatingsByRecipeId(recipeId);
        return new ResponseEntity<>(recipeRatings, HttpStatus.OK);
    }

    @GetMapping("/rating")
    public ResponseEntity<List<RatingResponseDto>> getRatingsByStatus(@RequestParam Integer approved) { //0: desaprobado, 1: aprobado, 2: pendientes
        if (Objects.nonNull(approved))
            return ResponseEntity.ok(ratingService.getRatingsByStatus(approved));
        return ResponseEntity.ok(ratingService.getRatings());
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
    public ResponseEntity<List<String>> getAllIngredients() {
        List<String> ingredients = ingredientService.getAllIngredients();
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

    @GetMapping("/{recipeId}/full-ingredients") // Obtener los ingredientes de una receta
    public ResponseEntity<List<IngredientResponseDto>> getFullRecipeIngredients(@PathVariable Long recipeId) {
        List<IngredientResponseDto> ingredients = recipeService.getFullIngredientsByRecipeId(recipeId);
        if (ingredients != null) {
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
