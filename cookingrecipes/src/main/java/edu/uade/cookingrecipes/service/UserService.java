package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.response.UserResponseDto;
import edu.uade.cookingrecipes.model.UserModel;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers(Boolean isStudent);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Long id);
    UserModel getUser();
    UserResponseDto getUserByToken();
    void deleteUser(Long userId);
}
