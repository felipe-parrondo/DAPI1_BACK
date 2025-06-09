package edu.uade.cookingrecipes.Entity.Embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}
