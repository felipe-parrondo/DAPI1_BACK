package edu.uade.cookingrecipes.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {

    private Long courseId;

    private boolean presentSite;

    private boolean presentClassroom;

    private Long siteId;

    private Long classroomId;

    private Long userId;

}
