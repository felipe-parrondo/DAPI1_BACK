package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.config.JwtService;
import edu.uade.cookingrecipes.dto.response.GetStudentUserResponseDto;
import edu.uade.cookingrecipes.dto.response.UserResponseDto;
import edu.uade.cookingrecipes.mapper.UserMapper;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.AuthenticationRepository;
import edu.uade.cookingrecipes.repository.UserRepository;
import edu.uade.cookingrecipes.service.AuthenticationService;
import edu.uade.cookingrecipes.service.RatingService;
import edu.uade.cookingrecipes.service.RecipeService;
import edu.uade.cookingrecipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    @Lazy
    private RatingService ratingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public List<GetStudentUserResponseDto> getAllUsers(Boolean isStudent) {
        List<UserModel> users = userRepository.findByIsStudent(isStudent).orElse(new ArrayList<>());
        return users.stream()
                .map(u -> {
                    String email = authenticationRepository.findByUsername(u.getUsername()).get().getEmail();
                    return new GetStudentUserResponseDto(
                            u.getUsername(),
                            email,
                            u.getAvatar(),
                            u.getId(),
                            u.getPaymentInformationModel().getUrlFrontDNI(),
                            u.getPaymentInformationModel().getUrlBackDNI(),
                            u.getPaymentInformationModel().getCardNumber()
                    );
                })
                .toList();
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        return users.stream()
                .map(user -> UserMapper.toDto(user, getAuthenticationByUser(user.getUsername()).getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> UserMapper.toDto(user, getAuthenticationByUser(user.getUsername()).getEmail()))
                .orElse(null);
    }

    @Override
    public UserResponseDto getUserByToken() {
        return UserMapper.toDto(getUser(),
                getAuthenticationByUser(getUser().getUsername()).getEmail());
    }

    @Override
    public UserModel getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = authenticationRepository.findByEmail(email)
                .map(AuthenticationModel::getUser)
                .orElseThrow(() -> new NoSuchElementException("user not found"));

        return user;
    }

    private AuthenticationModel getAuthenticationByUser(String userName) {
        return authenticationRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("Authentication not found for user: " + userName));
    }

    @Override
    public void deleteUser(Long userId) {
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        authenticationService.deleteAuthenticationByUserModel(userModel);
        recipeService.rejectRecipesByUserId(userModel.getId());
        ratingService.rejectRatingsByUserId(userModel.getId());
    }
}
