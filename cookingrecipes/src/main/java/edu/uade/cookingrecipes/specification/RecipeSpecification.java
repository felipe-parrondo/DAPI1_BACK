package edu.uade.cookingrecipes.specification;

import edu.uade.cookingrecipes.entity.Recipe;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
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
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (type != null && !type.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("dishType"), type));
            }

            if (hasIngredient != null && !hasIngredient.isEmpty()) {
                var join = root.join("ingredients");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(join.get("name")), "%" + hasIngredient.toLowerCase() + "%"));
            }

            if (hasntIngredient != null && !hasntIngredient.isEmpty()) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Recipe> subRoot = subquery.from(Recipe.class);
                Join<Object, Object> subJoin = subRoot.join("ingredients");

                subquery.select(subRoot.get("id"))
                        .where(criteriaBuilder.like(
                                criteriaBuilder.lower(subJoin.get("name")),
                                "%" + hasntIngredient.toLowerCase() + "%"
                        ));

                predicates.add(criteriaBuilder.not(root.get("id").in(subquery)));
            }


            if (user != null && !user.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("user").get("username")),
                        "%" + user.toLowerCase() + "%"
                ));
            }

            if (approved != null) {
                switch (approved) {
                    case 0 -> predicates.add(criteriaBuilder.equal(root.get("approved"), false));
                    case 1 -> predicates.add(criteriaBuilder.equal(root.get("approved"), true));
                    case 2 -> predicates.add(criteriaBuilder.isNull(root.get("approved")));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
