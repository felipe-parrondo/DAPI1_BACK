package edu.uade.cookingrecipes.exceptions;

import java.util.List;

public class UsernameAlreadyInUseException extends RuntimeException {

    private List<String> usernameSuggestions;

    public UsernameAlreadyInUseException(List<String> usernameSuggestions) {
        this.usernameSuggestions = usernameSuggestions;
    }

    public List<String> getUsernameSuggestions() {
        return usernameSuggestions;
    }
}
