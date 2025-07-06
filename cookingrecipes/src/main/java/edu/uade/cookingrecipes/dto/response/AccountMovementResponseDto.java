package edu.uade.cookingrecipes.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountMovementResponseDto {

    private Long id;

    @JsonProperty("dateTime")
    private String dateTime;

    private Double amount;

    private String reason;

    @JsonProperty("userId")
    private Long userId;
}
