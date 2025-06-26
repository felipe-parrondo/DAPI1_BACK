package edu.uade.cookingrecipes.dto.Request;

import edu.uade.cookingrecipes.dto.Response.ListResponseDto;

import java.io.Serializable;
import java.util.List;

public record GetListsByRecipeIdResponseDto (
    List<ListResponseDto> listRecipeResponse

) implements Serializable {}
