package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.dto.Request.ListRequestDto;
import edu.uade.cookingrecipes.dto.Response.ListResponseDto;
import edu.uade.cookingrecipes.service.ListService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "List Operations")
@RestController
@RequestMapping("/list")
public class ListController {

    @Autowired
    private ListService ListService;

    public ListController(ListService recipeListService) {
        this.ListService = recipeListService;
    }

    @GetMapping("/") //Obtener todas las listas
    public ResponseEntity<List<ListResponseDto>> getAllLists() {
        List<ListResponseDto> lists = ListService.getAllLists();
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    @PostMapping("/create") //Crear lista
    public ResponseEntity<ListResponseDto> createList(@RequestBody ListRequestDto requestDto) {
        ListResponseDto createdList = ListService.createList(requestDto);
        if(createdList != null) {
            return new ResponseEntity<>(createdList, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{list}/{recipe}") //Agregar receta a la lista
    public ResponseEntity<ListResponseDto> addRecipeToList(@PathVariable Long list, @PathVariable Long recipe) {
        ListResponseDto updatedList = ListService.addRecipeToList(list, recipe);
        if(updatedList != null) {
            return new ResponseEntity<>(updatedList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{list}") //Eliminar lista
    public ResponseEntity<Void> deleteList(@PathVariable Long list) {
        boolean isDeleted = ListService.deleteList(list);
        if(isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{list}/{recipe}") //Eliminar receta de la lista
    public ResponseEntity<ListResponseDto> removeRecipeFromList(@PathVariable Long list, @PathVariable Long recipe) {
        ListResponseDto updatedList = ListService.removeRecipeFromList(list, recipe);
        if(updatedList != null) {
            return new ResponseEntity<>(updatedList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
