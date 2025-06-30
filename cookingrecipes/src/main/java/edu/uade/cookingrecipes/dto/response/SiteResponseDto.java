package edu.uade.cookingrecipes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteResponseDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private List<String> photoUrl;
    private String location;
}
