package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByAddress(String address);
    Optional<List<UserModel>> findByIsStudent(Boolean isStudent);
}