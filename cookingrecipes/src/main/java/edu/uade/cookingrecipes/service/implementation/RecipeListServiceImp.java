package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.Entity.Recipe;
import edu.uade.cookingrecipes.Entity.RecipeList;
import edu.uade.cookingrecipes.dto.Request.RecipeListRequestDto;
import edu.uade.cookingrecipes.dto.Response.RecipeListResponseDto;
import edu.uade.cookingrecipes.mapper.RecipeListMapper;
import edu.uade.cookingrecipes.repository.RecipeListRepository;
import edu.uade.cookingrecipes.repository.RecipeRepository;
import edu.uade.cookingrecipes.service.RecipeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeListServiceImp implements RecipeListService {

    @Autowired
    private RecipeListRepository recipeListRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<RecipeListResponseDto> getAllLists() {
        List<RecipeList> recipeLists = recipeListRepository.findAll();
        return recipeLists.stream()
                .map(RecipeListMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeListResponseDto createList(RecipeListRequestDto requestDto) {
        List<Recipe> recipes = recipeRepository.findAllById(requestDto.getRecipeIds());
        RecipeList list = RecipeListMapper.toEntity(requestDto, recipes);
        RecipeList savedList = recipeListRepository.save(list);
        return RecipeListMapper.toDto(savedList);
    }

    @Override
    public RecipeListResponseDto addRecipeToList(Long listId, Long recipeId) {
        RecipeList recipeList = recipeListRepository.findById(listId).orElse(null);
        if (recipeList != null) {
            Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
            if (recipe != null && !recipeList.getRecipes().contains(recipe)) {
                recipeList.getRecipes().add(recipe);
            }
            recipeListRepository.save(recipeList);
            return RecipeListMapper.toDto(recipeList);
        }
        return null;
    }

    @Override
    public RecipeListResponseDto removeRecipeFromList(Long listId, Long recipeId) {
        RecipeList recipeList = recipeListRepository.findById(listId).orElse(null);
        if (recipeList == null) return null;
        recipeRepository.findById(recipeId).ifPresent(recipe -> recipeList.getRecipes().remove(recipe));
        recipeListRepository.save(recipeList);
        return RecipeListMapper.toDto(recipeList);
    }

    @Override
    public boolean deleteList(Long listId) {
        if (recipeListRepository.existsById(listId)) {
            recipeListRepository.deleteById(listId);
            return true;
        }
        return false;
    }

}
