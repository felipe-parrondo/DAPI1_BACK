package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.Entity.Recipe;
import edu.uade.cookingrecipes.Entity.RecipeList;
import edu.uade.cookingrecipes.config.JwtService;
import edu.uade.cookingrecipes.dto.Request.GetListsByRecipeIdResponseDto;
import edu.uade.cookingrecipes.dto.Request.ListRequestDto;
import edu.uade.cookingrecipes.dto.Response.ListResponseDto;
import edu.uade.cookingrecipes.dto.Response.RecipeResponseDto;
import edu.uade.cookingrecipes.mapper.ListMapper;
import edu.uade.cookingrecipes.mapper.RecipeMapper;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.AuthenticationRepository;
import edu.uade.cookingrecipes.repository.ListRepository;
import edu.uade.cookingrecipes.repository.RecipeRepository;
import edu.uade.cookingrecipes.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListServiceImpl implements ListService {

    @Autowired
    private ListRepository recipeListRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private JwtService jwtService; //TODO pasar a constructor

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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AuthenticationModel> authentication = authenticationRepository.findByEmail(email);
        if  (authentication.isPresent()) {
            List<Recipe> recipes = new ArrayList<>();
            if (requestDto.getRecipeIds() != null && !requestDto.getRecipeIds().isEmpty()) {
                recipes = recipeRepository.findAllById(requestDto.getRecipeIds());
            }
            RecipeList list = ListMapper.toEntity(requestDto, recipes);
            UserModel user = authentication.get().getUser();
            user.setId(authentication.get().getId());
            list.setUser(user);
            RecipeList savedList = recipeListRepository.save(list);
            return ListMapper.toDto(savedList);
        }
        else {
            return null;
        }
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
    @Override
    public ListResponseDto getListById(Long listId) {
        return recipeListRepository.findById(listId)
                .map(ListMapper::toDto)
                .orElse(null);
    }

    @Override
    public GetListsByRecipeIdResponseDto getListsByRecipeId(Long recipeId) {
        String email = jwtService.extractEmail(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        List<RecipeList> recipeList = recipeListRepository.findByUser_EmailAndRecipes_Id(email, recipeId)
                .orElseThrow(() -> new RuntimeException()); //TODO cambiarlo por NOT_FOUND exception

        return new GetListsByRecipeIdResponseDto(recipeList.stream()
                .map(ListMapper::toDto)
                .toList()
        );
    }
}