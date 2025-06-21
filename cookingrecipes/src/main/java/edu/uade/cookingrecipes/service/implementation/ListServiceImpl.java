package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.Entity.Recipe;
import edu.uade.cookingrecipes.Entity.RecipeList;
import edu.uade.cookingrecipes.dto.Request.ListRequestDto;
import edu.uade.cookingrecipes.dto.Response.ListResponseDto;
import edu.uade.cookingrecipes.mapper.ListMapper;
import edu.uade.cookingrecipes.repository.ListRepository;
import edu.uade.cookingrecipes.repository.RecipeRepository;
import edu.uade.cookingrecipes.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListServiceImpl implements ListService {

    @Autowired
    private ListRepository recipeListRepository;
    @Autowired
    private RecipeRepository recipeRepository;


    @Override
    public List<ListResponseDto> getAllLists() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<RecipeList> recipeLists = recipeListRepository.findAllByUserId(1L);
        return recipeLists.stream()
                .map(ListMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ListResponseDto createList(ListRequestDto requestDto) {
        List<Recipe> recipes = recipeRepository.findAllById(requestDto.getRecipeIds());
        RecipeList list = ListMapper.toEntity(requestDto, recipes);
        RecipeList savedList = recipeListRepository.save(list);
        return ListMapper.toDto(savedList);
    }

    @Override
    public ListResponseDto addRecipeToList(Long listId, Long recipeId) {
        RecipeList recipeList = recipeListRepository.findById(listId).orElse(null);
        if (recipeList != null) {
            Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
            if (recipe != null && !recipeList.getRecipes().contains(recipe)) {
                recipeList.getRecipes().add(recipe);
            }
            recipeListRepository.save(recipeList);
            return ListMapper.toDto(recipeList);
        }
        return null;
    }

    @Override
    public ListResponseDto removeRecipeFromList(Long listId, Long recipeId) {
        RecipeList recipeList = recipeListRepository.findById(listId).orElse(null);
        if (recipeList == null) return null;
        recipeRepository.findById(recipeId).ifPresent(recipe -> recipeList.getRecipes().remove(recipe));
        recipeListRepository.save(recipeList);
        return ListMapper.toDto(recipeList);
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
