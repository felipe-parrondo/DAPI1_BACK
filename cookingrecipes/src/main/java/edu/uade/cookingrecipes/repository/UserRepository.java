package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}