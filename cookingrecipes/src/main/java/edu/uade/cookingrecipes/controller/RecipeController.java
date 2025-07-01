package edu.uade.cookingrecipes.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uade.cookingrecipes.dto.request.UpdateRatingRequestDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    public ResponseEntity<?> validateRecipe(@RequestBody String recipeName) {
        Long recipeId = recipeService.validateRecipe(recipeName);
        if (recipeId != null) {
            logger.info("ON VALIDATING RECIPE NAME: RECIPE NAME IN USE WITH ID: " + recipeId);
            return new ResponseEntity<>(recipeId, HttpStatus.BAD_REQUEST);
        }
        logger.info("ON VALIDATING RECIPE NAME: RECIPE AVAILABLE");
        return ResponseEntity.ok(0L);
    }

    @PostMapping("/rating") //Valorar receta
    public ResponseEntity<RatingResponseDto> ratingRecipe(@RequestBody RatingRequestDto ratingRequestDto) {
        logger.info(ratingRequestDto.toString());
        RatingResponseDto rating = ratingService.ratingRecipe(ratingRequestDto.getRecipeId(), ratingRequestDto);
        if (rating != null) {
            return new ResponseEntity<>(rating, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/rating/{ratingId}/cr/{isApproved}") //Aprobar/rechazar valoracion
    public ResponseEntity<Void> reviewRatingRecipe(@PathVariable Long ratingId,@PathVariable Boolean isApproved) {
        logger.info(ratingId.toString()+" "+isApproved.toString());
        boolean ratedRecipe = ratingService.approveRating(ratingId, isApproved);
        if (ratedRecipe) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{recipeId}/cr/{isApproved}") //Aprobar/rechazar receta
    public ResponseEntity<Void> approveRecipe(@PathVariable Long recipeId,@PathVariable Boolean isApproved) {
        logger.info(recipeId.toString()+" "+isApproved.toString());
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

    @GetMapping("/rating/{ratingId}") //consigue valoracion de receta por id
    public ResponseEntity<RatingResponseDto> getRatingById(@PathVariable Long ratingId) {
        return ResponseEntity.ok(ratingService.getRatingById(ratingId));
    }

    @GetMapping("/{recipeId}") //Obtener receta por ID
    public ResponseEntity<RecipeResponseDto> getRecipeById(@PathVariable Long recipeId) {
        RecipeResponseDto recipe = recipeService.getRecipeById(recipeId);
        if (recipe != null) {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/rating") //Actualizar valoracion de receta
    public ResponseEntity<Void> updateRating(@RequestBody UpdateRatingRequestDto updateRatingRequestDto) {
        boolean updatedRating = ratingService.updateRating(updateRatingRequestDto);
        if (updatedRating) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/rating/recipe/{recipeId}") //Obtener todas las valoraciones de una receta
    public ResponseEntity<List<RatingResponseDto>> getRatingsByRecipeId(@PathVariable Long recipeId) {
        logger.info("REQUESTING RATINGS FOR RECIPE WITH ID " + recipeId.toString());
        List<RatingResponseDto> recipeRatings = ratingService.getRatingsByRecipeId(recipeId);
        logger.info(recipeRatings.toString());
        return new ResponseEntity<>(recipeRatings, HttpStatus.OK);
    }

    @GetMapping("/rating/recipe/{recipeId}/public") //Obtener todas las valoraciones de una receta publica
    public ResponseEntity<List<RatingResponseDto>> getRatingsByRecipeIdPublic(@PathVariable Long recipeId) {
        logger.info("REQUESTING RATINGS FOR RECIPE WITH ID " + recipeId.toString());
        List<RatingResponseDto> recipeRatings = ratingService.getRatingsByRecipeIdPublic(recipeId);
        logger.info(recipeRatings.toString());
        return new ResponseEntity<>(recipeRatings, HttpStatus.OK);
    }

    @GetMapping("/myRatings") //Obtener todas las valoraciones del usuario logueado
    public ResponseEntity<List<RatingResponseDto>> getMyRatings() {
        logger.info("REQUESTING RATINGS FOR USER");
        List<RatingResponseDto> myRatings = ratingService.getRatingsByUser();
        if (myRatings != null) {
            return new ResponseEntity<>(myRatings, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/myRecipes") //Obtener recetas del usuario logueado
    public ResponseEntity<List<RecipeResponseDto>> getMyRecipes() {
        logger.info("REQUESTING RECIPES FOR USER");
        List<RecipeResponseDto> myRecipes = recipeService.getRecipesByUser();
        if (myRecipes != null) {
            return new ResponseEntity<>(myRecipes, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/rating")
    public ResponseEntity<List<RatingResponseDto>> getRatingsByStatus(@RequestParam(required = false) @JsonProperty("isApproved") Boolean isApproved) {
        logger.info(Objects.nonNull(isApproved) ? isApproved.toString().toUpperCase(Locale.ROOT) + " RATINGS" : "PENDING RATINGS");
        List<RatingResponseDto> returnList;
        if (Objects.nonNull(isApproved)) {
            returnList = ratingService.getRatingsByStatus(isApproved);
            logger.info(returnList.toString());
            return ResponseEntity.ok(returnList);
        }
        returnList = ratingService.getRatings();
        logger.info(returnList.toString());
        return ResponseEntity.ok(returnList);
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

    @GetMapping("/ingredients") //Obtener todas los ingredientes
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
}