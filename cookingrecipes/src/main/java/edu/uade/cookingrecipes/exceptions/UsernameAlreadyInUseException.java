package edu.uade.cookingrecipes.exceptions;

import edu.uade.cookingrecipes.dto.auth.UserSuggestionResponseDto;

import java.util.List;

public class UsernameAlreadyInUseException extends RuntimeException {

    private UserSuggestionResponseDto usernameSuggestions;

    public UsernameAlreadyInUseException(List<String> usernameSuggestions) {
        this.usernameSuggestions = new UserSuggestionResponseDto(usernameSuggestions);
    }

    public UserSuggestionResponseDto getUsernameSuggestions() {
        return usernameSuggestions;
    }
}
