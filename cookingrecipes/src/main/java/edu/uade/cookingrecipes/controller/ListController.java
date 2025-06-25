package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.dto.Request.GetListsByRecipeIdResponseDto;
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
    private ListService listService;

    public ListController(ListService recipeListService) {
        this.listService = recipeListService;
    }

    @GetMapping("/") //Obtener todas las listas
    public ResponseEntity<List<ListResponseDto>> getAllLists() {
        List<ListResponseDto> lists = listService.getAllLists();
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    @PostMapping("/create") //Crear lista
    public ResponseEntity<ListResponseDto> createList(@RequestBody ListRequestDto requestDto) {
        ListResponseDto createdList = listService.createList(requestDto);
        if(createdList != null) {
            return new ResponseEntity<>(createdList, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{list}/{recipe}") //Agregar receta a la lista
    public ResponseEntity<ListResponseDto> addRecipeToList(@PathVariable Long list, @PathVariable Long recipe) {
        ListResponseDto updatedList = listService.addRecipeToList(list, recipe);
        if(updatedList != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{list}") //Eliminar lista
    public ResponseEntity<Void> deleteList(@PathVariable Long list) {
        boolean isDeleted = listService.deleteList(list);
        if(isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //TODO lo mismo que abajo (y en todo el controller)
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{list}/{recipe}") //Eliminar receta de la lista
    public ResponseEntity<ListResponseDto> removeRecipeFromList(@PathVariable Long list, @PathVariable Long recipe) {
        ListResponseDto updatedList = listService.removeRecipeFromList(list, recipe);
        if(updatedList != null) {
            return new ResponseEntity<>(HttpStatus.OK); //TODO lo mismo que abajo
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{listId}") //Obtener lista por ID
    public ResponseEntity<ListResponseDto> getListById(@PathVariable Long listId) {
        ListResponseDto list = listService.getListById(listId);
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK); //TODO Cambiar el flujo por algo manejado por excepcion custom
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{recipeId}") //Obtener lista por id de receta contenida
    public ResponseEntity<GetListsByRecipeIdResponseDto> getListByRecipeId(@PathVariable Long recipeId) {
        return ResponseEntity.ok(listService.getListsByRecipeId(recipeId));
    }
}
