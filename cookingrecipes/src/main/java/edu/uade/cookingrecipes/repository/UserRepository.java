package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {

}