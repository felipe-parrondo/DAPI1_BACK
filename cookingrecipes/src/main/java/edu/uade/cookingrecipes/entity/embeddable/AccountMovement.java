package edu.uade.cookingrecipes.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountMovement {
    private LocalDateTime dateTime;

    private Double amount;

    private String reason;
}
