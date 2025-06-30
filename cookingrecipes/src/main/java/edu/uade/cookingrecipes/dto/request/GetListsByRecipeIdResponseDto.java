package edu.uade.cookingrecipes.dto.request;

import edu.uade.cookingrecipes.dto.response.ListResponseDto;

import java.io.Serializable;
import java.util.List;

public record GetListsByRecipeIdResponseDto (
    List<ListResponseDto> listRecipeResponse

) implements Serializable {}
