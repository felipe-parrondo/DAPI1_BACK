package edu.uade.cookingrecipes.specification;

import edu.uade.cookingrecipes.Entity.Recipe;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RecipeSpecification {

    public static Specification<Recipe> withFilters(
            String name,
            String type,
            String hasIngredient,
            String hasntIngredient,
            String user,
            Integer approved
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (type != null && !type.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }

            if (hasIngredient != null && !hasIngredient.isEmpty()) {
                predicates.add(criteriaBuilder.isMember(hasIngredient, root.get("ingredients")));
            }

            if (hasntIngredient != null && !hasntIngredient.isEmpty()) {
                predicates.add(criteriaBuilder.not(criteriaBuilder.isMember(hasntIngredient, root.get("ingredients"))));
            }

            if (user != null && !user.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("username"), user));
            }

            if (approved != null) {
                predicates.add(criteriaBuilder.equal(root.get("approved"), approved));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
