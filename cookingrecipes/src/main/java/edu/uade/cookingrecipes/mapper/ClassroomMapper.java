package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.dto.response.ClassroomResponseDto;
import edu.uade.cookingrecipes.entity.Classroom;

public class ClassroomMapper {
    public static ClassroomResponseDto toDto(Classroom classroom) {
        if (classroom == null) {
            return null;
        }

        return new ClassroomResponseDto(
                classroom.getId(),
                classroom.getClassNumber(),
                classroom.getSite()
        );
    }
}
