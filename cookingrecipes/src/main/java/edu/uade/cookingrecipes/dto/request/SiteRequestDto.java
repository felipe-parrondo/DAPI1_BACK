package edu.uade.cookingrecipes.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteRequestDto {
    private String name;
    private String address;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    private String email;
    @JsonProperty("photoUrl")
    private List<String> photoUrl;
    private String location;
}
