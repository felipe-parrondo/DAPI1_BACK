package edu.uade.cookingrecipes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Classroom {
    @Id
    private Long id;

    private String classNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Site site;
}
