package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.dto.response.UserResponseDto;
import edu.uade.cookingrecipes.mapper.UserMapper;
import edu.uade.cookingrecipes.model.AuthenticationModel;
import edu.uade.cookingrecipes.model.UserModel;
import edu.uade.cookingrecipes.repository.AuthenticationRepository;
import edu.uade.cookingrecipes.repository.UserRepository;
import edu.uade.cookingrecipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

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

    private UserModel getUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = authenticationRepository.findByEmail(email)
                .map(AuthenticationModel::getUser)
                .orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("User not found: " + email);
        }
        return user;
    }

    private AuthenticationModel getAuthenticationByUser(String userName) {
        return authenticationRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("Authentication not found for user: " + userName));
    }
}
