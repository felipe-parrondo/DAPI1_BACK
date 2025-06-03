package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.dto.Request.RecipeListRequestDto;
import edu.uade.cookingrecipes.dto.Response.RecipeListResponseDto;
import edu.uade.cookingrecipes.service.RecipeListService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "List Operations")
@RestController
@RequestMapping("/recipe_list")
public class RecipeListController {

    @Autowired
    private final RecipeListService recipeListService;

    public RecipeListController(RecipeListService recipeListService) {
        this.recipeListService = recipeListService;
    }

    @GetMapping("/ ") //Obtener todas las listas
    public ResponseEntity<List<RecipeListResponseDto>> getAllLists() {
        List<RecipeListResponseDto> lists = recipeListService.getAllLists();
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    @PostMapping("/create") //Crear lista
    public ResponseEntity<RecipeListResponseDto> createList(@RequestBody RecipeListRequestDto requestDto) {
        RecipeListResponseDto createdList = recipeListService.createList(requestDto);
        if(createdList != null) {
            return new ResponseEntity<>(createdList, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add/{list}/{recipe}") //Agregar receta a la lista
    public ResponseEntity<RecipeListResponseDto> addRecipeToList(@PathVariable Long list, @PathVariable Long recipe) {
        RecipeListResponseDto updatedList = recipeListService.addRecipeToList(list, recipe);
        if(updatedList != null) {
            return new ResponseEntity<>(updatedList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{list}") //Eliminar lista
    public ResponseEntity<Void> deleteList(@PathVariable Long list) {
        boolean isDeleted = recipeListService.deleteList(list);
        if(isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{list}/{recipe}") //Eliminar receta de la lista
    public ResponseEntity<RecipeListResponseDto> removeRecipeFromList(@PathVariable Long list, @PathVariable Long recipe) {
        RecipeListResponseDto updatedList = recipeListService.removeRecipeFromList(list, recipe);
        if(updatedList != null) {
            return new ResponseEntity<>(updatedList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
