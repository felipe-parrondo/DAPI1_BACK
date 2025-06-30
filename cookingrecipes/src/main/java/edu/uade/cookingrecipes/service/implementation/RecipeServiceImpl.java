package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.entity.embeddable.IngredientEmbeddable;
import edu.uade.cookingrecipes.entity.embeddable.Media;
import edu.uade.cookingrecipes.entity.embeddable.Step;
import edu.uade.cookingrecipes.entity.Recipe;
import edu.uade.cookingrecipes.dto.request.RecipeRequestDto;
import edu.uade.cookingrecipes.dto.response.RecipeResponseDto;
import edu.uade.cookingrecipes.dto.response.IngredientResponseDto;
import edu.uade.cookingrecipes.entity.enums.DishTypes;

import edu.uade.cookingrecipes.mapper.RecipeMapper;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.AuthenticationRepository;
import edu.uade.cookingrecipes.repository.RecipeRepository;
import edu.uade.cookingrecipes.repository.UserRepository;
import edu.uade.cookingrecipes.service.ImageService;
import edu.uade.cookingrecipes.service.IngredientService;
import edu.uade.cookingrecipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final IngredientService ingredientService;

    private final UserRepository userRepository;

    private final AuthenticationRepository authenticationRepository;

    private final ImageService imageService;


    private final String MEDIA_RESOURCE_IDENTIFIER = "recipes";

    public RecipeServiceImpl(RecipeRepository recipeRepository, IngredientService ingredientService, UserRepository userRepository,
                             ImageService imageService, AuthenticationRepository authenticationRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public List<RecipeResponseDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeResponseDto> filterRecipes(Specification<Recipe> spec, String sort) {
        List<Recipe> result = recipeRepository.findAll(spec);
        List<RecipeResponseDto> recipeList = result.stream()
                .map(RecipeMapper::toDto)
                .toList();

        Comparator<RecipeResponseDto> comparator = switch (sort) {
            case "user" -> Comparator.comparing(RecipeResponseDto::getUsername, String.CASE_INSENSITIVE_ORDER);
            case "recent" -> Comparator.comparing(RecipeResponseDto::getId).reversed();
            default -> Comparator.comparing(RecipeResponseDto::getDishType, String.CASE_INSENSITIVE_ORDER);
        };

        return recipeList.stream()
                .sorted(comparator)
                .toList();
    }
    @Override
    @Transactional
    public RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto, List<MultipartFile> files) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        AuthenticationModel authentication = authenticationRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + email));

        UserModel user = userRepository.findById(authentication.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found by ID: " + authentication.getId()));

        Recipe recipe;
        boolean isUpdate = recipeRequestDto.getId() != null && recipeRepository.existsById(recipeRequestDto.getId());

        if (isUpdate) {
            recipe = recipeRepository.findById(recipeRequestDto.getId())
                    .orElseThrow(() -> new NoSuchElementException("Recipe not found: " + recipeRequestDto.getId()));

            recipe.setName(recipeRequestDto.getName());
            recipe.setDescription(recipeRequestDto.getDescription());
            recipe.setServings(recipeRequestDto.getServings());
            recipe.setDishType(recipeRequestDto.getDishType());
            recipe.setUser(user);

            // Clear and replace collections
            recipe.getPhotos().clear();
            if (recipeRequestDto.getPhotos() != null) {
                List<String> fileNames = recipeRequestDto.getPhotos().stream()
                        .map(this::getFileNameFromUrl)
                        .collect(Collectors.toList());
                recipe.getPhotos().addAll(fileNames);
            }
            recipe.setServings(recipeRequestDto.getServings());
            recipe.setDishType(recipeRequestDto.getDishType());
            recipe.setUser(user);

            // Clear and replace collections
            recipe.getPhotos().clear();
            if (recipeRequestDto.getPhotos() != null) {
                List<String> fileNames = recipeRequestDto.getPhotos().stream()
                        .map(this::getFileNameFromUrl)
                        .collect(Collectors.toList());
                recipe.getPhotos().addAll(fileNames);
            }

            recipe.getIngredients().clear();
            if (recipeRequestDto.getIngredients() != null) {
                recipe.getIngredients().addAll(recipeRequestDto.getIngredients());
            }

            recipe.getSteps().clear();
            if (recipeRequestDto.getSteps() != null) {
                for (Step step : recipeRequestDto.getSteps()) {
                    step.setRecipe(recipe);

                    if (step.getMedia() != null) {
                        List<Media> updatedMedia = step.getMedia().stream()
                                .map(media -> new Media(getFileNameFromUrl(media.getUrl()), media.getMediaType()))
                                .collect(Collectors.toList());
                        step.setMedia(updatedMedia);
                    }

                    recipe.getSteps().add(step);
                }
            }

            eliminarArchivosNoReferenciados(recipeRequestDto, recipe.getId());

        } else {
            recipe = RecipeMapper.toEntity(recipeRequestDto);
            recipe.setUser(user);
            recipe.setApproved(null);

            // Solo guardar nombres de archivos
            if (recipe.getPhotos() != null) {
                List<String> fileNames = recipe.getPhotos().stream()
                        .map(this::getFileNameFromUrl)
                        .collect(Collectors.toList());
                recipe.setPhotos(fileNames);
            }

            if (recipe.getSteps() != null) {
                for (Step step : recipe.getSteps()) {
                    step.setRecipe(recipe);

                    if (step.getMedia() != null) {
                        List<Media> updatedMedia = step.getMedia().stream()
                                .map(media -> new Media(getFileNameFromUrl(media.getUrl()), media.getMediaType()))
                                .collect(Collectors.toList());
                        step.setMedia(updatedMedia);
                    }
                }
            }
        }

        Recipe savedRecipe = recipeRepository.save(recipe);
        if (files != null) {
            for (MultipartFile f : files) {
                try {
                    imageService.saveFile(f, savedRecipe.getId().toString(), "recipes");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Construir DTO con URLs absolutas
        //String baseMediaUrl = buildMediaUrlPrefix(savedRecipe.getId());

       /* List<String> updatedPhotos = new ArrayList<>();
        if (savedRecipe.getPhotos() != null) {
            for (String photo : savedRecipe.getPhotos()) {
                updatedPhotos.add(baseMediaUrl + photo);
            }
        }

        List<Step> updatedSteps = new ArrayList<>();
        if (savedRecipe.getSteps() != null) {
            for (Step step : savedRecipe.getSteps()) {
                List<Media> rebuiltMedia = new ArrayList<>();
                if (step.getMedia() != null) {
                    for (Media media : step.getMedia()) {
                        rebuiltMedia.add(new Media(baseMediaUrl + media.getUrl(), media.getMediaType()));
                    }
                }

                Step updatedStep = new Step();
                updatedStep.setDescription(step.getDescription());
                updatedStep.setMedia(rebuiltMedia);
                updatedSteps.add(updatedStep);
            }
        }

        RecipeResponseDto responseDto = RecipeMapper.toDto(savedRecipe);
        responseDto.setPhotos(updatedPhotos);
        responseDto.setSteps(updatedSteps);*/

        return RecipeMapper.toDto(savedRecipe);
    }

    private String getFileNameFromUrl(String url) {
        if (url == null || url.isBlank()) return "";
        return url.substring(url.lastIndexOf('/') + 1);
    }

    private void eliminarArchivosNoReferenciados(RecipeRequestDto dto, Long recipeId) {
        Set<String> archivosReferenciados = new HashSet<>();

        // Fotos
        if (dto.getPhotos() != null) {
            archivosReferenciados.addAll(dto.getPhotos().stream()
                    .map(this::getFileNameFromUrl)
                    .collect(Collectors.toSet()));
        }

        // Steps
        if (dto.getSteps() != null) {
            for (Step step : dto.getSteps()) {
                if (step.getMedia() != null) {
                    for (Media media : step.getMedia()) {
                        archivosReferenciados.add(getFileNameFromUrl(media.getUrl()));
                    }
                }
            }
        }

        String folderPath = "media/recipes/" + recipeId;
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] filesInDir = folder.listFiles();
            if (filesInDir != null) {
                for (File file : filesInDir) {
                    if (!archivosReferenciados.contains(file.getName())) {
                        boolean deleted = file.delete();
                        if (!deleted) {
                            System.err.println("No se pudo eliminar: " + file.getAbsolutePath());
                        } else {
                            System.out.println("Archivo eliminado: " + file.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Long validateRecipe(String recipeName) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = authenticationRepository.findByEmail(email)
                .map(AuthenticationModel::getUser)
                .orElse(null);
        if (user == null) throw new NoSuchElementException("User not found: " + email);

        boolean exists = recipeRepository.existsByNameAndUser(recipeName, user);
        if (exists) {
            throw new IllegalArgumentException("Recipe with name '" + recipeName
                    + "' already exists for user: " + user.getName());
        }
        return user.getId();
    }

    @Override
    public boolean approveRecipe(Long recipeId, Boolean isApproved) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe == null) return false;
        recipe.setApproved(isApproved);
        recipeRepository.save(recipe);
        for (IngredientEmbeddable ingredient : recipe.getIngredients()) {
            ingredientService.saveIfNotExists(ingredient.getName());
        }
        return true;
    }

    @Override
    public List<RecipeResponseDto> getRecentRecipes() {
        return recipeRepository.findTop3ByOrderByIdDesc().stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteRecipe(Long recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            return false;
        }
        recipeRepository.deleteById(recipeId);
        return true;
    }

    @Override
    public RecipeResponseDto getRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .map(RecipeMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<String> getRecipeIngredients(Long recipeId) {
        List<IngredientEmbeddable> ingredients = recipeRepository.findById(recipeId)
                .map(Recipe::getIngredients)
                .orElse(null);
        if (ingredients == null) return null;
        return ingredients.stream()
                .map(IngredientEmbeddable::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRecipeDishTypes(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .map(recipe -> {
                    DishTypes tipo = DishTypes.valueOf(recipe.getDishType());
                    return Collections.singletonList(tipo.getNombreAmigable());
                })
                .orElse(null);
    }

    @Override
    public List<String> getAllDishTypes() {
        return Arrays.stream(DishTypes.values())
                .map(DishTypes::getNombreAmigable)
                .toList();
    }

    @Override
    public List<IngredientResponseDto> getFullIngredientsByRecipeId(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe == null || recipe.getIngredients() == null) return null;

        return recipe.getIngredients().stream().map(ingredient -> {
            IngredientResponseDto dto = new IngredientResponseDto();
            dto.setName(ingredient.getName());
            dto.setQuantity(ingredient.getQuantity());
            dto.setUnidad(ingredient.getUnidad());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void rejectRecipesByUserId(Long userId) {
        List<Recipe> recipeList = recipeRepository.findByUser_Id(userId);
        recipeList.forEach(r -> {
            r.setApproved(false);
            recipeRepository.save(r);
        });
    }
}
