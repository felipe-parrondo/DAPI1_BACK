package edu.uade.cookingrecipes.dto.request.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteAttendanceRequestDto {
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("siteId")
    private Long siteId;
}
