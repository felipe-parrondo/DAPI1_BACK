package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.entity.Recipe;
import edu.uade.cookingrecipes.entity.RecipeList;
import edu.uade.cookingrecipes.config.JwtService;
import edu.uade.cookingrecipes.dto.request.ListRequestDto;
import edu.uade.cookingrecipes.dto.response.ListResponseDto;
import edu.uade.cookingrecipes.exceptions.NotFoundException;
import edu.uade.cookingrecipes.mapper.ListMapper;
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
import java.util.Collections;
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
        Optional<AuthenticationModel> authentication = authenticationRepository.findByEmail(email);
        if  (authentication.isPresent()) {
            List<RecipeList> recipeLists = recipeListRepository.findAllByUserId(authentication.get().getId());
            return recipeLists.stream()
                    .map(ListMapper::toDto)
                    .collect(Collectors.toList());
        }
        else {
            throw new NotFoundException("Usuario no encontrado");
        }
    }

    @Override
    public boolean createList(ListRequestDto requestDto) {
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
            recipeListRepository.save(list);
            return true;
        }
        else {
            return false;
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
    @Override //Obtener solo las listas aprobadas
    public ListResponseDto getListById(Long listId) {
        RecipeList recipeListWithAllRecipes = recipeListRepository.findById(listId).orElse(null);
        RecipeList recipeListWithApprovedRecipes = new RecipeList();

        assert recipeListWithAllRecipes != null;
        recipeListWithApprovedRecipes.setRecipes(recipeListWithAllRecipes.getRecipes()
                .stream()
                .filter(r -> Boolean.TRUE.equals(r.getApproved()))
                .toList()
        );

        recipeListWithApprovedRecipes.setId(recipeListWithAllRecipes.getId());
        recipeListWithApprovedRecipes.setName(recipeListWithAllRecipes.getName());
        recipeListWithApprovedRecipes.setUser(recipeListWithAllRecipes.getUser());
        return ListMapper.toDto(recipeListWithApprovedRecipes);
    }

    @Override
    public List<ListResponseDto> getListsByRecipeId(Long recipeId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        /*String email = jwtService.extractEmail(
                SecurityContextHolder.getContext().getAuthentication().getCredentials().toString()
        );*/

        Optional<AuthenticationModel> authentication = authenticationRepository.findByEmail(email);
        if (authentication.isEmpty()) {
            throw new NotFoundException("Usuario no encontrado");
        }

        List<RecipeList> recipeList = recipeListRepository
                .findByUser_IdAndRecipes_Id(authentication.get().getId(), recipeId)
                .orElse(Collections.emptyList());

        /*return new GetListsByRecipeIdResponseDto(
                recipeList.stream().map(ListMapper::toDto).toList()
        );*/
        return recipeList.stream().map(ListMapper::toDto).toList();
    }

}