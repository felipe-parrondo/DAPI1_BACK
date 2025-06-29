package edu.uade.cookingrecipes.mapper.embeddable;

import edu.uade.cookingrecipes.dto.request.embeddable.PracticeRequestDto;
import edu.uade.cookingrecipes.dto.response.embeddable.PracticeResponseDto;
import edu.uade.cookingrecipes.entity.embeddable.Practice;

import java.util.List;

public class PracticeMapper {
    public static List<Practice> toEntity(List<PracticeRequestDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return null;

        return dtos.stream().map(dto -> {
            Practice practice = new Practice();
            practice.setDescription(dto.getDescription());
            practice.setInstructions(dto.getInstructions());
            practice.setRecommendations(dto.getRecommendations());
            return practice;
        }).toList();
    }

    public static List<PracticeResponseDto> toDto(List<Practice> practices) {
        if (practices == null || practices.isEmpty()) return null;

        return practices.stream().map(practice -> {
            PracticeResponseDto dto = new PracticeResponseDto();
            dto.setDescription(practice.getDescription());
            dto.setInstructions(practice.getInstructions());
            dto.setRecommendations(practice.getRecommendations());
            return dto;
        }).toList();
    }
}
