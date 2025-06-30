package edu.uade.cookingrecipes.specification;

import edu.uade.cookingrecipes.entity.Recipe;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("user")), "%" + user.toLowerCase() + "%"));
            }

            if (approved != null) {
                switch (approved) {
                    case 0 -> predicates.add(criteriaBuilder.equal(root.get("approved"), false));
                    case 1 -> predicates.add(criteriaBuilder.equal(root.get("approved"), true));
                    case 2 -> predicates.add(criteriaBuilder.isNull(root.get("approved")));
                    default -> {}
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
