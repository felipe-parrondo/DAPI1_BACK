package edu.uade.cookingrecipes.controller;

import edu.uade.cookingrecipes.dto.response.UserResponseDto;
import edu.uade.cookingrecipes.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Api(value = "User Operations")
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/") // Get all users
    public ResponseEntity<List<UserResponseDto>> getAllUsers (@RequestParam Boolean isStudent) {
        if (Objects.nonNull(isStudent)) {
            return ResponseEntity.ok(userService.getAllUsers(isStudent));
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}") // Get user by ID
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        UserResponseDto user = userService.getUserById(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me") // Get user by token
    public ResponseEntity<UserResponseDto> getUserByToken() {
        UserResponseDto user = userService.getUserByToken();
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}